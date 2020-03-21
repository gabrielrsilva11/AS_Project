/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.threads;

import as_project.monitors.GranaryMonitor;
import as_project.monitors.PathMonitor;
import as_project.monitors.StandingAreaMonitor;
import as_project.monitors.StoreHouseMonitor;

/**
 *
 * @author manuelcura
 */
public class FarmerThread extends Thread {
    public Thread current;
    private final StoreHouseMonitor storeHouse;
    private final StandingAreaMonitor standingAreaMonitor;
    private final PathMonitor pathMonitor;
    private final GranaryMonitor granaryMonitor;
    private boolean stopped;

    public FarmerThread(StoreHouseMonitor storeHouse, StandingAreaMonitor standingAreaMonitor, PathMonitor pathMonitor, GranaryMonitor granaryMonitor) {
        this.storeHouse = storeHouse;
        this.standingAreaMonitor = standingAreaMonitor;
        this.pathMonitor = pathMonitor;
        this.granaryMonitor = granaryMonitor;
        this.current = new Thread(this);
        this.stopped = false;
    }

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
    
    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
