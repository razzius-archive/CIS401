package orchestration;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import org.apache.log4j.Logger;
import orchestration.VM;
import orchestration.RemoteHostInterface;

public class RemoteHost {
    private final String ip;
    private static Logger logger = Logger.getLogger(RemoteHost.class.getName());
    private RemoteHostInterface rmiServer;

    public RemoteHost(String ip) {
        this.ip = ip;
        try {
        	rmiServer = (RemoteHostInterface)Naming.lookup("rmi://" + ip + "/");
        } catch (RemoteException e) {
        	logger.fatal("Unable to connect to remote server " + e);
        } catch (Exception e) {
        	logger.fatal(e);
        	System.exit(1);
        }
    }

    public String getIp() {
        return this.ip;
    }

    public void bootVM(VM vm) throws RemoteException {
		rmiServer.bootVM(vm);
    }

}
