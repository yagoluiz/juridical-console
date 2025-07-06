FROM openjdk:21-jdk-slim

ENV LEIN_VERSION=2.9.10

RUN apt-get update && \
    apt-get install -y --no-install-recommends curl ca-certificates bash rlwrap unzip && \
    curl -o /usr/local/bin/lein https://raw.githubusercontent.com/technomancy/leiningen/${LEIN_VERSION}/bin/lein && \
    chmod +x /usr/local/bin/lein && \
    lein && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY project.clj /app/
RUN lein deps

COPY . /app

RUN lein compile

ENTRYPOINT ["lein", "run"]
