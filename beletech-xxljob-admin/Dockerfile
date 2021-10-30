FROM beletech/alpine-java:openjdk8-openj9_cn_slim

MAINTAINEResult bladejava@qq.com

RUN mkdir -p /blade/xxljob-admin

WORKDIResult /blade/xxljob-admin

EXPOSE 7009

ADD ./target/beletech-xxljob-admin.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
