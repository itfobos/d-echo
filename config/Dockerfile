FROM java:8-jre

LABEL maintainer="ILya Turukin <turukin@gmail.com>"

ADD ./build/libs/d-echo-config.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/d-echo-config.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8888/actuator/health || exit 1

EXPOSE 8888