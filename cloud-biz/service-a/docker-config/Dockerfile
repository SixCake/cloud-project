FROM registry.cn-hangzhou.aliyuncs.com/acs/maven:3-jdk-8 as builder

ENV MY_HOME=/app
RUN mkdir -p $MY_HOME
WORKDIR $MY_HOME
ADD pom.xml $MY_HOME

# get all the downloads out of the way
RUN ["/usr/local/bin/mvn-entrypoint.sh","mvn","verify","clean","--fail-never"]

# add source
ADD . $MY_HOME

# run maven verify
RUN ["/usr/local/bin/mvn-entrypoint.sh","mvn","verify"]

# Second stage - build image
FROM openjdk:8-jre-alpine

COPY --from=builder /app/target/*.jar /app.jar

ENV JAVA_OPTS=""
ENV SERVER_PORT 9601

EXPOSE ${SERVER_PORT}

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/urandom -jar /app.jar" ]
# 指定维护者的名字
MAINTAINER sixCake