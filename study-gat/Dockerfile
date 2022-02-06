FROM java:8
EXPOSE 8160

VOLUME /temp
ADD target/*.jar /study-gat.jar
RUN bash -c 'touch /study-gat.jar'
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx300m","/study-gat.jar","--spring.profile.active=prod"]