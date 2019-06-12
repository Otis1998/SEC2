package nju.edu.cinema.po;

import java.sql.Time;

public class RefundStrategy {
    private int id;
    private String name;
    private int refundable;
    private Time availableTime;
    private int charge;
    private int state;

    public RefundStrategy() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getRefundable() {
        return refundable;
    }

    public void setRefundable(int refundable) {
        this.refundable = refundable;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public Time getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(Time availableTime) {
        this.availableTime = availableTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
