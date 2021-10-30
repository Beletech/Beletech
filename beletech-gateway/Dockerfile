FROM jdk8u292-b10_openj9-0.26.0-alpine-slim

MAINTAINER betaxue@126.com

RUN mkdir -p /beletech/gateway

WORKDIR /beletech/gateway

EXPOSE 80

ADD ./target/beletech-gateway.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
