FROM openjdk:21-jdk-slim

RUN apt-get update && apt-get install -y --no-install-recommends \
    curl ca-certificates bash rlwrap unzip && \
    rm -rf /var/lib/apt/lists/*

ENV LEIN_VERSION 2.9.10
RUN curl -o /usr/local/bin/lein https://raw.githubusercontent.com/technomancy/leiningen/$LEIN_VERSION/bin/lein && \
    chmod +x /usr/local/bin/lein && \
    lein

WORKDIR /app
COPY . /app

RUN lein deps

CMD ["lein", "run"]
