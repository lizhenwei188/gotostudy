pipeline {

      agent {
        node {
          label 'maven'
        }
      }

      parameters {
        string(name:'PROJECT_NAME', defaultValue: 'study-gat', description:'')
        string(name:'PROJECT_VERSION', defaultValue: 'v0.1Beta' ,description:'')
      }

      environment {
          DOCKER_CREDENTIAL_ID = 'dockerhub-id'
          GITHUB_CREDENTIAL_ID = 'github-id'
          GITEE_CREDENTIAL_ID = 'gitee-id'
          KUBECONFIG_CREDENTIAL_ID = 'gotostudy-kubeconfig'
          REGISTRY = 'docker.io'
          GITHUB_ACCOUNT = '53Hertzl'
          GITEE_ACCOUNT = 'LiZhenwei188'
          DOCKERHUB_NAMESPACE = '53hertz'
          SONAR_CREDENTIAL_ID= 'sonar-token'
          BRANCH_NAME = 'master'
      }

      stages {

            stage('拉取代码') {
              steps {
                git(credentialsId: 'gitee-id', url: 'https://gitee.com/lizhenwei188/gotostudy.git', branch: 'master', changelog: true, poll: false)
                container ('maven') {
                  sh "mvn clean install -Dmaven.test.skip=true -gs `pwd`/settings.xml"
                }

              }
            }


            stage('代码质量分析') {
              steps {
                container ('maven') {
                  withCredentials([string(credentialsId: "$SONAR_CREDENTIAL_ID", variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv('sonar') {
                     sh "mvn sonar:sonar  -gs `pwd`/settings.xml -Dsonar.login=$SONAR_TOKEN"
                    }
                  }
                  timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                  }
                }
              }
            }

            stage ('构建项目推送镜像到dockerhub') {
              steps {
                container ('maven') {
                  sh 'mvn  -Dmaven.test.skip=true -gs `pwd`/settings.xml clean package'
                  sh 'cd $PROJECT_NAME && docker build -f Dockerfile -t $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER .'
                  withCredentials([usernamePassword(passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,credentialsId : "$DOCKER_CREDENTIAL_ID" ,)]) {
                      sh 'echo "$DOCKER_PASSWORD" | docker login $REGISTRY -u "$DOCKER_USERNAME" --password-stdin'
                      sh 'docker tag  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:latest '
                      sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:latest'
                  }
                }
              }
            }

            stage('部署项目到k8s集群中') {
              when{
                branch 'master'
              }
              steps {
                input(id: "deployment-$PROJECT_NAME-$PROJECT_VERSION", message: "是否将 $PROJECT_NAME:$PROJECT_VERSION 部署到集群中?")
                kubernetesDeploy(configs: "$PROJECT_NAME/deployment/**", enableConfigSubstitution: true, kubeconfigId: "$KUBECONFIG_CREDENTIAL_ID")
              }
            }

            stage('发布版本到gitee并推送镜像到dockerhub'){
              when{
                expression{
                  return params.PROJECT_VERSION =~ /v.*/
                }
              }
              steps {
                container ('maven') {
                  input(id: "publish-$PROJECT_NAME-$PROJECT_VERSION", message: "是否发布 $PROJECT_NAME-$PROJECT_VERSION 镜像版本?")
                    withCredentials([usernamePassword(credentialsId: "$GITEE_CREDENTIAL_ID", passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                      sh 'git config --global user.email "lizhenwei188@foxmail.com" '
                      sh 'git config --global user.name "lizhenwei" '
                      sh 'git tag -a $PROJECT_VERSION -m "$PROJECT_VERSION" '
                      sh 'git push http://$GIT_USERNAME:$GIT_PASSWORD@gitee.com/$GITEE_ACCOUNT/gotostudy.git --tags --ipv4'
                    }
                  sh 'docker tag  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:$PROJECT_VERSION '
                  sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:$PROJECT_VERSION '
                  sh 'docker rmi -f  $REGISTRY/$DOCKERHUB_NAMESPACE/$PROJECT_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER'
                }
              }
            }


      }


}