package util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
* Message - Class that contains the message structure
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class Message {
    /**
     * Car registration number
     */
    private String carReg;
    /**
     * Timestamp of the collected message
     */
    private int ts;
    /**
     * Type of the message
     */
    private int type;
    /**
     * Additional information
     */
    private String extraInfo;

    /**
     * Message class constructor
     * 
     * @param carReg Car registration number
     * @param ts Timestamp of the collected message
     * @param type Type of the message
     * @param extraInfo Additional information
     */
    @JsonCreator
    public Message(@JsonProperty("carReg") String carReg, 
            @JsonProperty("ts") int ts, 
            @JsonProperty("type") int type,
            @JsonProperty("extraInfo") String extraInfo) {
        super();
        this.carReg = carReg;
        this.ts = ts;
        this.type = type;
        this.extraInfo = extraInfo;
    }
    
    /**
     * Method to get the car registration number
     * 
     * @return car registration number
     */
    public String getCarReg() {
        return carReg;
    }
    
    /**
     * Method to set the car registration number
     * 
     * @param carReg car registration number
     */
    public void setCarReg(String carReg) {
        this.carReg = carReg;
    }

    /**
     * Method to get the timestamp of the collected message
     * 
     * @return timestamp of the collected message
     */
    public int getTs() {
        return ts;
    }

    /**
     * Method to set the timestamp of the collected message
     * 
     * @param ts timestamp of the collected message
     */
    public void setTs(int ts) {
        this.ts = ts;
    }

    /**
     * Method to get the type of the message
     * 
     * @return type of the message
     */
    public int getType() {
        return type;
    }

    /**
     * Method to set the type of the message
     * 
     * @param type type of the message
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Method to get additional information
     * 
     * @return additional information
     */
    public String getExtraInfo() {
        return extraInfo;
    }

    /**
     * Method to set additional information
     * 
     * @param extraInfo additional information
     */
    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
