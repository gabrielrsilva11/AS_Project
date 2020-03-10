/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.threads;

import as_project.models.FarmerStatus;
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
    private FarmerStatus status;
    private int[][] position;

    public FarmerThread(StoreHouseMonitor storeHouse, StandingAreaMonitor standingAreaMonitor, PathMonitor pathMonitor, GranaryMonitor granaryMonitor) {
        this.storeHouse = storeHouse;
        this.standingAreaMonitor = standingAreaMonitor;
        this.pathMonitor = pathMonitor;
        this.granaryMonitor = granaryMonitor;
    }

    @Override
    public void run() {
        storeHouse.goToStoreHouse(this);
        //standingAreaMonitor.enterStandingArea(this);
    }

    public int[][] getPosition() {
        return position;
    }

    public void setPosition(int[][] position) {
        this.position = position;
    }

    public FarmerStatus getStatus() {
        return status;
    }

    public void setStatus(FarmerStatus status) {
        this.status = status;
    }
}
