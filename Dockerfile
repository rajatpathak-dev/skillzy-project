FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY build/libs/SkillzyWebApp-0.0.1-SNAPSHOT.jar app.jar

ENV SECURITY_USER=user
ENV SECURITY_PASS=123

CMD ["java", "-jar", "app.jar"]
