all: repl

.PHONY: repl
repl:
	./gradlew run --console=plain

test:
	./gradlew test