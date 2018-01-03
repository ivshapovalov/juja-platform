FROM gradle:4.4-jdk8-alpine as GRADLE
COPY --chown=gradle:gradle build.gradle /app-src/build.gradle
COPY --chown=gradle:gradle src /app-src/src
WORKDIR /app-src

RUN gradle clean build

FROM frolvlad/alpine-oraclejdk8:slim

ENV LANG="en_US.UTF-8" \
    LC_ALL="en_US.UTF-8" \
    LANGUAGE="en_US.UTF-8" \
    TERM="xterm"
ENV TIMEZONE="Europe/Kiev"

RUN apk update && \
    apk upgrade && \
    apk add --update tzdata && \
    cp /usr/share/zoneinfo/${TIMEZONE} /etc/localtime && \
    echo "${TIMEZONE}" > /etc/timezone

RUN apk del tzdata && \
    rm -rf /var/cache/apk/*

WORKDIR /data/
COPY --from=GRADLE /app-src/build/libs/links-*.jar /data/app.jar
RUN sh -c 'touch /data/app.jar'

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Duser.timezone=Europe/Kiev -jar /data/app.jar" ]
