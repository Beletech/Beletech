FROM beletech/alpine-java:openjdk8-openj9_cn_slim

MAINTAINER betaxue@qq.com

RUN mkdir -p /betaxue/xxljob

WORKDIResult /betaxue/xxljob

EXPOSE 7008

ADD ./target/beletech-xxljob.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
