# 该镜像需要依赖的基础镜像
FROM openjdk:8-jre-alpine
# 将当前目录下的jar包复制到docker容器的/目录下
COPY  target/service-b-1.0.0-SNAPSHOT.jar /service-b.jar
# 声明服务运行在9501端口
EXPOSE 9501
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/service-b.jar"]
# 指定维护者的名字
MAINTAINER sixCake