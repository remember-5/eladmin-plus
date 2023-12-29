# ---- Base Node ----
FROM maven:3.8.5-openjdk-17 AS base
RUN mkdir -p /root/.m2
COPY settings.xml /root/.m2/

# ---- Build ----
FROM base AS build
WORKDIR /app
COPY . .
RUN mvn install -T 4

# ---- Run ----
FROM openjdk:17-alpine3.14 AS run
WORKDIR /java
COPY --from=build /app/eladmin-system/target/eladmin-template.jar ./app.jar

# 声明一个环境参数用来动态启用配置文件 默认dev
ENV ACTIVE=dev
# 暴露端口
EXPOSE 8000
EXPOSE 58080

CMD ["sh","-c","java -jar app.jar --spring.profiles.active=${ACTIVE}"]
