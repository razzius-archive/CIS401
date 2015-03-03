package orchestration;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import org.apache.log4j.Logger;
import orchestration.VM;
import orchestration.RemoteHostInterface;

public class RemoteHostServer implements RemoteHostInterface {
	private static Logger logger = Logger.getLogger(RemoteHostServer.class.getName());

	public RemoteHostServer() throws RemoteException {
		super();
	}

	public void bootVM(VM vm) throws RemoteException {
		System.out.println("bootVM message received");
	}

	public static void main(String[] args) {
		try {
			RemoteHostInterface server = new RemoteHostServer();
			LocateRegistry.createRegistry(1099);
			Naming.rebind("rmi://0.0.0.0/", server);
			logger.info("Server is running...");
		} catch (Exception e) {
			logger.fatal(e);
		}
	}
}
