FROM java:8
EXPOSE 8080

VOLUME /temp
ADD target/*.jar /study-vod.jar
RUN bash -c 'touch' /study-vod.jar
ENTRYPOINT ["java","-jar","/study-vod.jar","--spring.profile.active=prod"]