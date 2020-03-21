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

    private final StoreHouseMonitor storeHouse;
    private final StandingAreaMonitor standingAreaMonitor;
    private final PathMonitor pathMonitor;
    private final GranaryMonitor granaryMonitor;

    public FarmerThread(StoreHouseMonitor storeHouse, StandingAreaMonitor standingAreaMonitor, PathMonitor pathMonitor, GranaryMonitor granaryMonitor) {
        this.storeHouse = storeHouse;
        this.standingAreaMonitor = standingAreaMonitor;
        this.pathMonitor = pathMonitor;
        this.granaryMonitor = granaryMonitor;
    }

    @Override
    public void run() {
        while(true) {
            storeHouse.goToStoreHouse(this);
            standingAreaMonitor.enterStandingArea(this);
            pathMonitor.walkToThePath(this);
            granaryMonitor.enterTheGranary(this);
            granaryMonitor.collectTheCorn(this);
            pathMonitor.walkToThePathReverse(this);
        }
    }
}
