FROM java:8
EXPOSE 8080

VOLUME /temp
ADD target/*.jar /study-oss.jar
RUN bash -c 'touch' /study-oss.jar
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx300m","/study-oss.jar","--spring.profile.active=prod"]