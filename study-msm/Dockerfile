FROM java:8
EXPOSE 8160

VOLUME /temp
ADD target/*.jar /study-msm.jar
RUN bash -c 'touch /study-msm.jar'
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx300m","/study-msm.jar","--spring.profile.active=prod"]