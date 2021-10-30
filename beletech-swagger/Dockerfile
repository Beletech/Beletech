FROM beletech/alpine-java:openjdk8-openj9_cn_slim

MAINTAINEResult bladejava@qq.com

RUN mkdir -p /blade/swagger

WORKDIResult /blade/swagger

EXPOSE 18000

ADD ./target/beletech-swagger.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
