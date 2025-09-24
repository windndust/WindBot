FROM bellsoft/liberica-openjre-alpine:21
LABEL org.opencontainers.image.source https://github.com/windndust/WindBot
ARG JAR_NAME
ENV APP_HOME=/app
COPY target/$JAR_NAME $APP_HOME/$JAR_NAME
WORKDIR $APP_HOME
ENV JAR=$JAR_NAME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $JAR"]