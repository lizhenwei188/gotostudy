FROM java:8
EXPOSE 8160

VOLUME /temp
ADD target/*.jar /study-ord.jar
RUN bash -c 'touch /study-ord.jar'
#ENTRYPOINT ["java","-jar","-Xms128m","-Xmx300m","/study-ord.jar","--spring.profile.active=prod"]
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx300m","/study-ord.jar"]