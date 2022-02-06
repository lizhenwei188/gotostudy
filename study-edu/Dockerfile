FROM java:8
EXPOSE 8080

VOLUME /temp
ADD target/*.jar /study-edu.jar
RUN bash -c 'touch /study-edu.jar'
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx300m","/study-edu.jar","--spring.profile.active=prod"]