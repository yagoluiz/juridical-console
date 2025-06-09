# Juridical Console (WIP)

Worker console responsible for web scraping and identifying legal processes from the Projudi system for the Justice
Tribunal of Brazil.

## Project Overview

This project is based on [juridical-worker](https://github.com/yagoluiz/juridical-worker), but implemented in Clojure.

## Running the Project

### Using Leiningen

#### Configuration

When running the project with Leiningen (`lein run` or `lein repl`), update your credentials directly in the
`resources/config.edn` file:

```clojure
:legal-process-user     "your-username"
:legal-process-password "your-password"
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
