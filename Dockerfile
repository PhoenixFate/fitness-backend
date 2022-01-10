FROM java:8
EXPOSE 9080

VOLUME /tmp
ADD fitness-backend.jar  /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]