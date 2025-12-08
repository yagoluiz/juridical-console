.PHONY: run-selenium lein-run lein-repl run-compose down-compose logs-selenium logs-juridical-console

run-selenium:
	docker run --rm --name selenium \
		-p 4444:4444 \
		-p 7900:7900 \
		--shm-size="2g" \
		selenium/standalone-chromium:125.0

lein-run:
	lein run

lein-repl:
	lein with-profile dev repl

run-compose:
	docker-compose up --build

down-compose:
	docker-compose down

logs-selenium:
	docker-compose logs -f selenium

logs-juridical-console:
	docker-compose logs -f juridical-console
