FROM java:8
EXPOSE 8080

VOLUME /temp
ADD target/*.jar /study-ord.jar
RUN bash -c 'touch' /study-ord.jar
ENTRYPOINT ["java","-jar","/study-ord.jar","--spring.profile.active=prod"]