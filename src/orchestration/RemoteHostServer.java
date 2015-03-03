package orchestration;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import orchestration.VM;
import orchestration.RemoteHostInterface;


public class RemoteHostServer extends UnicastRemoteObject implements RemoteHostInterface {
	static final long serialVersionUID = 0;
	private static transient Logger logger = Logger.getLogger(RemoteHostServer.class.getName());

	public RemoteHostServer() throws RemoteException {
		super();
	}

	public void bootVM(VM vm) throws RemoteException {
		System.out.println("bootVM message received");
	}

	public static void main(String[] args) {
		try {
			RemoteHostInterface server = new RemoteHostServer();
			Registry registry = LocateRegistry.createRegistry(1099);

			Naming.rebind("rmi://localhost/remote", server);
			logger.info("Server is running...");
		} catch (Exception e) {
			System.out.println(e);
			logger.fatal(e);
		}
	}
}
