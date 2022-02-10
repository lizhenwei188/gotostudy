FROM java:8
EXPOSE 8160

VOLUME /temp
ADD target/*.jar /study-cms.jar
RUN bash -c 'touch /study-cms.jar'
#ENTRYPOINT ["java","-jar","-Xms128m","-Xmx300m","/study-cms.jar","--spring.profile.active=prod"]
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx300m","/study-cms.jar"]