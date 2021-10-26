FROM maven:3.8.3-adoptopenjdk-11

WORKDIR /service-instance-1
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run

##
## Build stage
##
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY src /service-instance-1/src
#COPY pom.xml /service-instance-1/
#RUN mvn -f /service-instance-1/pom.xml clean package
#
##
## Package stage
##
#FROM openjdk:11-jre-slim
#COPY --from=build /service-instance-1/target/*.jar service-instance-1-*.jar
#EXPOSE 8081
#ENTRYPOINT ["java","-jar","service-instance-1-*.jar"]