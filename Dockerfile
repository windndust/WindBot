FROM bellsoft/liberica-openjre-alpine:21
ARG JAR_NAME
RUN echo "Hello"
RUN echo "Jar Name $JAR_NAME"
ENV APP_HOME=/app
RUN echo "App Home $APP_HOME"
COPY target/$JAR_NAME $APP_HOME/$JAR_NAME
WORKDIR $APP_HOME
ENV JAR=$JAR_NAME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $JAR"]