full:
	make build
	make run

build: 
	cd "$(shell pwd)/src"  && javac Application.java

run: 
	cd "$(shell pwd)/src" && java Application

clean:
	rm **/*.class