FROM turukin/ubuntu-jdk:v11

LABEL maintainer="ILya Turukin <turukin@gmail.com>"

ADD ./build/libs/d-echo-echo-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/d-echo-echo-service.jar"]

EXPOSE 8778