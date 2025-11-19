FROM amazoncorretto:17
ARG JAR_FILE=build/libs/*.jar
# 작업 디렉토리를 만들고 이동 (중요)
WORKDIR /app
# JAR 파일을 /app 안으로 복사
COPY ${JAR_FILE} /app/app.jar
# 실행
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/app.jar"]