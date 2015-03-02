package test;

import org.junit.*;
import static org.junit.Assert.*;

import orchestration.StateManager;
import orchestration.Request;
import orchestration.CustomerResponse;



public class ExampleTest {

	@Test
	public void exampleTest1() {
		StateManager sm = new StateManager();
		Request request = new Request("r4","s3_0","s3_1",72432,44,"s0-s1-s3",12000,1);
        CustomerResponse cr = StateManager.queryAlgorithmSolver(request);
        System.out.println(cr.accepted);
        assertTrue(cr.accepted);
	}

	@Test
	public void exampleTest2() {

	}

}