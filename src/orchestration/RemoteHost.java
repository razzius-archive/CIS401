package orchestration;


public class RemoteHost {
    private final String ip;

    public RemoteHost(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return this.ip;
    }
}
