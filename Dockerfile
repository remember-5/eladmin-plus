# 构建容器
FROM openjdk:8-jre-alpine
# Author
MAINTAINER wangjiahao Shanghai 1332661444@qq.com
# 指定路径
WORKDIR /eladmin

# 复制jar文件到路径
ADD ./eladmin-system/target/eladmin-template.jar ./app.jar

# 挂载几个有用的文件夹 比如日志
VOLUME ["/tmp","/logs"]
# 创建目录
RUN mkdir -p /eladmin

# 声明一个环境参数用来动态启用配置文件 默认dev
ENV ACTIVE=dev

# 暴露端口
EXPOSE 8000
#指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
ENTRYPOINT ["java", "-jar", "app.jar","--spring.profiles.active=${ACTIVE}"]
