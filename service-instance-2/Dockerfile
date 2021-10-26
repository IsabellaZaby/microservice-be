FROM maven:3.8.3-adoptopenjdk-11

WORKDIR /service-instance-2
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run