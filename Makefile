CLASSES=Request,RequestHandler,HttpURLConnectionExample
SOURCES=src/orchestration/{$(CLASSES)}.java
CLASSPATH=bin

all:
	javac -cp $(CLASSPATH) $(SOURCES) -d bin

runserver:
	java -cp $(CLASSPATH) orchestration.RequestHandler 8080 

runrequest:
	java -cp $(CLASSPATH) orchestration.HttpURLConnectionExample


