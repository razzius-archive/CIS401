CLASSES=Request,RequestHandler,HttpURLConnectionExample,OrchestrationLayer,AlgorithmSolver,AlgorithmSolverInterface,StateManager,Analytics,Machine,Link,Service,Node,Switch,Tenant,VM
SOURCES=src/orchestration/{$(CLASSES)}.java
CLASSPATH=bin

all:
	javac -cp $(CLASSPATH) $(SOURCES) -d bin

run:
	java -cp $(CLASSPATH) orchestration.OrchestrationLayer

runserver:
	java -cp $(CLASSPATH) orchestration.RequestHandler 8080 

runrequest:
	java -cp $(CLASSPATH) orchestration.HttpURLConnectionExample


