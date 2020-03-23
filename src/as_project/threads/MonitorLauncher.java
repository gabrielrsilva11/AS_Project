package as_project.threads;
import as_project.monitors.*;
/**
* MonitorLauncher - Extension of the thread class that launches a specific monitor method
* depending on the message received by the Farm Infrastructure
* 
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class MonitorLauncher implements Runnable{
    /**
     * Message received by the Farm Infrastructure with the task to perform.
     */
    private String message = null;
    /**
     * Instance of a store house monitor
     */
    private StoreHouseMonitor shMonitor = null;
    /**
     * Instance of a standing area monitor
     */
    private StandingAreaMonitor saMonitor = null;
    /**
     * Instance of a granary monitor
     */
    private GranaryMonitor grMonitor = null;
    /**
     * Number of farmers to work
     */
    private int numFarmers;
    
    /**
     * Constructor for the "prepare" case
     * @param message task to perform received by the Farm infrastructure
     * @param shMonitor storehouse monitor
     * @param numFarmers number of farmers to work
     */
    public MonitorLauncher(String message, StoreHouseMonitor shMonitor, int numFarmers){
        this.message = message;
        this.shMonitor = shMonitor;
        this.numFarmers = numFarmers;
    }
    /**
     * Constructor for the "start" action
     * @param message task to perform received by the Farm infrastructure
     * @param saMonitor standing area monitor
     */
    public MonitorLauncher(String message, StandingAreaMonitor saMonitor){
        this.message = message;
        this.saMonitor = saMonitor;
    }
    /**
     * Constructor for the "collect" and "return" actions;
     * @param message task to perform received by the Farm infrastructure
     * @param grMonitor granary monitor
     */
    public MonitorLauncher(String message, GranaryMonitor grMonitor){
        this.message = message;
        this.grMonitor = grMonitor;
    }
    /**
     * Override of the run method. Launches the correct method for the farmers to advance
     * depending on the message received by the Farm Infrastructure
     */
    @Override
    public void run(){
        switch(message){
            case "prepare":
                shMonitor.prepare(numFarmers);
                break;
            case "start":
                saMonitor.proceedToThePath();
                break;
            case "collect":
                grMonitor.collect();
                break;
            case "return":
                grMonitor.returnToTheBeginning();
                break;
        }
    }
}