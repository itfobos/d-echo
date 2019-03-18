FROM turukin/ubuntu-jdk:v11

LABEL maintainer="ILya Turukin <turukin@gmail.com>"

ADD ./build/libs/d-echo-discovery-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/d-echo-discovery-service.jar"]

EXPOSE 8761