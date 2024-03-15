ARG APP_BUILDER=openjdk:17-alpine
ARG APP_RUNNER=$APP_BUILDER

FROM $APP_BUILDER as builder

WORKDIR /build

COPY *.gradle gradle.* gradlew ./
COPY gradle ./gradle
RUN ./gradlew --version

COPY . .
RUN ./gradlew build bootJar


FROM $APP_RUNNER
WORKDIR /app
COPY --from=builder /build/build/libs/checkhttps-0.0.1-SNAPSHOT.jar ./

ENTRYPOINT java -jar checkhttps-0.0.1-SNAPSHOT.jar
