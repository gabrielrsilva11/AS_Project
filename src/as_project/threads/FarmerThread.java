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

    public FarmerThread(StoreHouseMonitor storeHouse, StandingAreaMonitor standingAreaMonitor, PathMonitor pathMonitor, GranaryMonitor granaryMonitor) {
        this.storeHouse = storeHouse;
        this.standingAreaMonitor = standingAreaMonitor;
        this.pathMonitor = pathMonitor;
        this.granaryMonitor = granaryMonitor;
        this.current = new Thread(this);
    }

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            storeHouse.goToStoreHouse(this);
            standingAreaMonitor.enterStandingArea(this);
            pathMonitor.walkToThePath(this);
            granaryMonitor.enterTheGranary(this);
            granaryMonitor.collectTheCorn(this);
            pathMonitor.walkToThePathReverse(this);
        }
    }
}
