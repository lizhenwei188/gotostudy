FROM java:8
EXPOSE 8080

VOLUME /temp
ADD target/*.jar /study-usr.jar
RUN bash -c 'touch' /study-usr.jar
ENTRYPOINT ["java","-jar","/study-usr.jar","--spring.profile.active=prod"]