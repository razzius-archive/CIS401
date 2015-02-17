CLASSES=Request,RequestHandler,HttpURLConnectionExample,OrchestrationLayer,AlgorithmSolver,AlgorithmSolverInterface,StateManager,Analytics,Machine,Link,Service,Node,Switch,Tenant,VM
SOURCES=src/orchestration/{$(CLASSES)}.java
CLASSPATH=bin

all:
	javac -cp $(CLASSPATH) $(SOURCES) -d bin

orchestration:
	java -cp $(CLASSPATH) orchestration.OrchestrationLayer

runrequest:
	java -cp $(CLASSPATH) orchestration.HttpURLConnectionExample


