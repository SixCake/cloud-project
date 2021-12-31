# 该镜像需要依赖的基础镜像
FROM java:8
# 将当前目录下的jar包复制到docker容器的/目录下
ADD service-a-1.0.0-SNAPSHOT.jar /service-a.jar
# 运行过程中创建一个service-a.jar文件
RUN bash -c 'touch /service-a.jar'
# 声明服务运行在9601端口
EXPOSE 9601
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","/service-a.jar"]
# 指定维护者的名字
MAINTAINER sixCake