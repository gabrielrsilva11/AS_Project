package LoadBalancer;

import java.util.List;
import model.ChooseServer;
import utils.ConnectionInfo;
import utils.Request;

/**
 * MonitorInterface - Interface to be implemented by the monitor. Contains
 * methods relevant to the load balancer monitor.
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public interface MonitorInterface {

    public int registerServer(ConnectionInfo connectionInfo);

    public List<Request> closeServer(int serverId);

    public ChooseServer chooseServer();

    public int generateClientId();

    public void addClientRequest(Request re);

    public ConnectionInfo removeServerConnection(int serverId);

    public void completeClientRequest(Request re);

    public void heartbeatStatus(int serverId, int status);
}
