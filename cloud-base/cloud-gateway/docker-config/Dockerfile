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

FROM openjdk:8-jre-alpine
# 将当前目录下的jar包复制到docker容器的/目录下
COPY --from=builder target/cloud-gateway-1.0.0-SNAPSHOT.jar /cloud-gateway.jar
# 声明服务运行在9201端口
EXPOSE 9201
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/cloud-gateway.jar"]
# 指定维护者的名字
MAINTAINER sixCake