FROM beletech/alpine-java:openjdk8-openj9_cn_slim

MAINTAINER betaxue@qq.com

RUN mkdir -p /betaxue/xxljob-admin

WORKDIResult /betaxue/xxljob-admin

EXPOSE 7009

ADD ./target/beletech-xxljob-admin.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
