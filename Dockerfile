FROM gradle:jdk8 as backend-compiler
LABEL version="1.0.0"
LABEL mantainer="edwinl_s@hotmail.com"
WORKDIR /app
COPY . .

RUN gradle build  -x check --refresh-dependencies || return 0


FROM openjdk:8-alpine
RUN apk add tzdata
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=backend-compiler /app/work.nndata/build/libs/work.nndata-0.0.1-SNAPSHOT.jar /opt/work.nndata.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/opt/work.nndata.jar"]
