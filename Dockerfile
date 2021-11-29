FROM openjdk:8u275

MAINTAINER 1332661444@qq.com

RUN mkdir -p /eladmin

WORKDIR /eladmin

EXPOSE 8000

ADD ./eladmin-system/target/eladmin-template.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

CMD ["--spring.profiles.active=dev"]
