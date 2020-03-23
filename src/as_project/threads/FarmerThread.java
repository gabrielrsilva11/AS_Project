package as_project.threads;

import as_project.monitors.GranaryMonitor;
import as_project.monitors.PathMonitor;
import as_project.monitors.StandingAreaMonitor;
import as_project.monitors.StoreHouseMonitor;

/**
* FarmerThread - Farmers thread class, contains the operations each farmer is supposed to do
* 
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class FarmerThread extends Thread {
    /**
     * Thread variable to save the current thread context
     */
    public Thread current;
    /**
     * Stores the Store House Monitor
     */
    private final StoreHouseMonitor storeHouse;
    /**
     * Stores the Standing Area Monitor
     */
    private final StandingAreaMonitor standingAreaMonitor;
    /**
     * Stores the Path Monitor
     */
    private final PathMonitor pathMonitor;
    /**
     * Stores the Granary Monitor
     */
    private final GranaryMonitor granaryMonitor;
    /**
     * Boolean to check whether the execution should be restarted or not
     */
    private boolean stopped;
    
    /**
     * Farmer Thread class constructor
     * @param storeHouse Store House monitor 
     * @param standingAreaMonitor Standing Area monitor 
     * @param pathMonitor Path monitor
     * @param granaryMonitor Granary Monitor
     */
    public FarmerThread(StoreHouseMonitor storeHouse, StandingAreaMonitor standingAreaMonitor, PathMonitor pathMonitor, GranaryMonitor granaryMonitor) {
        this.storeHouse = storeHouse;
        this.standingAreaMonitor = standingAreaMonitor;
        this.pathMonitor = pathMonitor;
        this.granaryMonitor = granaryMonitor;
        this.current = new Thread(this);
        this.stopped = false;
    }
    /**
     * Override of the run method. Sequential order of operations each farmer has to execute
     */
    @Override
    public void run() {
        while(!Thread.interrupted()) {
            stopped = false;
            storeHouse.goToStoreHouse(this);
            if (stopped)
                continue;
            standingAreaMonitor.enterStandingArea(this);
            if (stopped)
                continue;
            pathMonitor.walkToThePath(this);
            if (stopped)
                continue;
            granaryMonitor.enterTheGranary(this);
            if (stopped)
                continue;
            granaryMonitor.collectTheCorn(this);
            if (stopped)
                continue;
            pathMonitor.walkToThePathReverse(this);
        }
    }
     /** 
     * Method to stop operations and set the thread in its initial state
     * 
     */
    public void setStopped(boolean stopped) {
        this.stopped = stopped;
        //Reset stored corn when stopped?
        storeHouse.setStoreCorn();
        standingAreaMonitor.stopped();
        pathMonitor.stopped();
        granaryMonitor.stopped();

    }
}
