FROM jdk8u292-b10_openj9-0.26.0-alpine-slim

MAINTAINER betaxue@126.com

RUN mkdir -p /beletech/resource

WORKDIR /beletech/resource

EXPOSE 8010

ADD ./target/beletech-resource.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
