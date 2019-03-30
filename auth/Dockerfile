FROM turukin/ubuntu-jdk:v11

LABEL maintainer="ILya Turukin <turukin@gmail.com>"

ADD ./build/libs/d-echo-auth.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/d-echo-auth.jar"]

EXPOSE 8080