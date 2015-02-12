CLASSES=Request,RequestHandler,HttpURLConnectionExample
SOURCES=src/orchestration/{$(CLASSES)}.java
CLASSPATH=bin

all:
	javac -cp $(CLASSPATH) $(SOURCES) -d bin

run:
	java -cp $(CLASSPATH) orchestration.RequestHandler 8080 
