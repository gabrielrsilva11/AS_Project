/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author gabri
 */
public class Message {

    private String carReg;
    private int ts;
    private int type;
    private String extraInfo;
    
    public Message(String carReg, int ts, int type) {
        this.carReg = carReg;
        this.ts = ts;
        this.type = type;
        this.extraInfo = null;
    }
    
    public Message(String carReg, int ts, int type, String extraInfo) {
        this.carReg = carReg;
        this.ts = ts;
        this.type = type;
        this.extraInfo = extraInfo;
    }

    public String getCarReg() {
        return carReg;
    }

    public void setCar_reg(String carReg) {
        this.carReg = carReg;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtra_info(String extraInfo) {
        this.extraInfo = extraInfo;
    }
    
}
