FROM java:8
EXPOSE 8080

VOLUME /temp
ADD target/*.jar /study-sta.jar
RUN bash -c 'touch' /study-sta.jar
ENTRYPOINT ["java","-jar","/study-sta.jar","--spring.profile.active=prod"]