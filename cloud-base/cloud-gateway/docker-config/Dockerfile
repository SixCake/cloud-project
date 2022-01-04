FROM maven:3.5.0-jdk-8-alpine AS builder

# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

# package jar
RUN mvn clean package

FROM openjdk:8-jre-alpine
# 将当前目录下的jar包复制到docker容器的/目录下
COPY --from=builder target/cloud-gateway-1.0.0-SNAPSHOT.jar /cloud-gateway.jar
# 声明服务运行在9201端口
EXPOSE 9201
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/cloud-gateway.jar"]
# 指定维护者的名字
MAINTAINER sixCake