FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle/ gradle/

RUN ./gradlew dependencies --no-daemon

COPY src/ src/
RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/CFT_CRM-1.0.0-release.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
