FROM maven:3.8.3-adoptopenjdk-11

WORKDIR /config-server
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run

EXPOSE 8084
