package nju.edu.cinema.vo;

import java.sql.Time;

public class RefundStrategyForm {
    private int id;
    private String name;
    private int refundable;
    private Time availableTime;
    private int charge;
    private int state;

    public RefundStrategyForm(int id, String name, int refundable, Time availableTime, int charge, int state) {
        this.id = id;
        this.name = name;
        this.refundable = refundable;
        this.availableTime = availableTime;
        this.charge = charge;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRefundable() {
        return refundable;
    }

    public void setRefundable(int refundable) {
        this.refundable = refundable;
    }

    public Time getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(Time availableTime) {
        this.availableTime = availableTime;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
