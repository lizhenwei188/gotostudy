FROM java:8
EXPOSE 8160

VOLUME /temp
ADD target/*.jar /study-sta.jar
RUN bash -c 'touch /study-sta.jar'
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx300m","/study-sta.jar","--spring.profile.active=prod"]
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx300m","/study-sta.jar"]