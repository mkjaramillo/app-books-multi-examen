FROM eclipse-temurin:17.0.7_7-jre-alpine


WORKDIR /app


COPY build/libs ./libs
COPY build/libs/books-1.0-SNAPSHOT.jar .


CMD ["java","-jar","books-1.0-SNAPSHOT.jar"]

