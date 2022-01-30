FROM java:8
EXPOSE 8080

VOLUME /temp
ADD target/*.jar /study-msm.jar
RUN bash -c 'touch' /study-msm.jar
ENTRYPOINT ["java","-jar","/study-msm.jar","--spring.profile.active=prod"]