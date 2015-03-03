package orchestration;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteHostInterface extends Remote {
    public void bootVM(VM vm) throws RemoteException;
}
