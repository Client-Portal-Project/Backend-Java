
FROM maven:3.8.4-openjdk-11-slim as BUILDER
ARG VERSION=0.0.1
LABEL version = "1.0"
LABEL description = "This is the base docker image for Project X backend java app."
LABEL maintainer = "projectxrev@gmail.com"

WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src

RUN mvn clean package
COPY target/Client-Portal.jar target/application.jar

FROM openjdk:11

WORKDIR /app/

COPY --from=BUILDER /build/target/application.jar /app/

EXPOSE 3000
CMD java -jar /app/application.jar
