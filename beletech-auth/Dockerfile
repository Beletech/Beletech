FROM jdk8u292-b10_openj9-0.26.0-alpine-slim

MAINTAINER betaxue@126.com

RUN echo -e 'https://mirrors.aliyun.com/alpine/v3.6/main/\nhttps://mirrors.aliyun.com/alpine/v3.6/community/' /etc/apk/repositories \
 && apk --no-cache add ttf-dejavu fontconfig

RUN mkdir -p /beletech/auth

WORKDIR /beletech/auth

EXPOSE 8100

ADD ./target/beletech-auth.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
