FROM openjdk:8
EXPOSE 8080
ADD target/mlr-it-consulting-repas-app.jar mlr-it-consulting-repas-app.jar
ENTRYPOINT ["java","-jar","/mlr-it-consulting-repas-app.jar"]