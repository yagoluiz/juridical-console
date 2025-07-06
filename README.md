[![Build Juridical Console](https://github.com/yagoluiz/juridical-console/actions/workflows/build.yml/badge.svg)](https://github.com/yagoluiz/juridical-console/actions/workflows/build.yml)

# Juridical Console

Worker console responsible for web scraping and identifying legal processes from the Projudi system for the Justice
Tribunal of Brazil.

## Project Overview

This project is based on [juridical-worker](https://github.com/yagoluiz/juridical-worker) implemented in Clojure.

## Running the Project

### Using Leiningen

#### Configuration

When running the project with Leiningen update your credentials directly in the
`resources/config.edn` file:

```clojure
:legal-process-user     "your-username"
:legal-process-password "your-password"
:senvia-api-token       "your-api-token"
:senvia-sms-from        "your-zenvia-id"
:senvia-sms-to          "your-to-phone-number"
```

#### Run the selenium image

```sh
make run-selenium
```

#### Run the application

```sh
make lein-run
```

Or using the REPL

```sh
make lein-repl
```

### Using Docker

#### Configuration

When running the project with Docker, create a new `.env` file and update your credentials:

```sh
LEGAL_PROCESS_USER=your-username
LEGAL_PROCESS_PASSWORD=your-password
SENVIA_API_TOKEN=your-api-token
SENVIA_SMS_FROM=your-zenvia-id
SENVIA_SMS_TO=your-to-phone-number
```

#### Run docker-compose

```sh
make run-compose
```

#### Down docker-compose

```sh
make down-compose
```

#### Logs docker-compose

```sh
make logs-selenium
```

```sh
make logs-juridical-console
```
