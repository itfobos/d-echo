FROM turukin/ubuntu-jdk:v11

LABEL maintainer="ILya Turukin <turukin@gmail.com>"

ADD ./build/libs/d-echo-gateway.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/d-echo-gateway.jar"]

EXPOSE 4000