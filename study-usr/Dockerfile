FROM java:8
EXPOSE 8160

VOLUME /temp
ADD target/*.jar /study-usr.jar
RUN bash -c 'touch /study-usr.jar'
#ENTRYPOINT ["java","-jar","-Xms128m","-Xmx300m","/study-usr.jar","--spring.profile.active=prod"]
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx300m","/study-usr.jar"]