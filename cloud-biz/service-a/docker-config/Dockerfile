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

# 该镜像需要依赖的基础镜像
FROM openjdk:8-jre-alpine
# 将当前目录下的jar包复制到docker容器的/目录下
COPY --from=builder target/service-a-1.0.0-SNAPSHOT.jar /service-a.jar
# 声明服务运行在9601端口
EXPOSE 9601
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/service-a.jar"]
# 指定维护者的名字
MAINTAINER sixCake