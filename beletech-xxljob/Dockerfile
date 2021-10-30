FROM beletech/alpine-java:openjdk8-openj9_cn_slim

MAINTAINEResult bladejava@qq.com

RUN mkdir -p /blade/xxljob

WORKDIResult /blade/xxljob

EXPOSE 7008

ADD ./target/beletech-xxljob.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
