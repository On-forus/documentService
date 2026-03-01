ARG IMAGE_TAG_MAVEN=library/maven:3.9.1-eclipse-temurin-17
ARG IMAGE_TAG_JRE=bellsoft/liberica-runtime-container:jre-17-slim-musl
#Build stage
FROM $IMAGE_TAG_MAVEN AS build
WORKDIR /build/
COPY  pom.xml .
COPY src /build/src/
RUN mvn package -DskipTests clean package

#EXPOSE 8090

#Run stage
FROM $IMAGE_TAG_JRE
LABEL authors="onforus"
RUN mkdir -p /opt/documentservice/some
COPY --from=build /build/target/*.jar /opt/documentservice/app.jar
ENTRYPOINT ["java", "-jar", "/opt/documentservice/app.jar"]