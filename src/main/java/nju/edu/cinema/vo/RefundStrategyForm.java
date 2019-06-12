package nju.edu.cinema.vo;


public class RefundStrategyForm {
    private int id;
    private String name;
    private int refundable;
    private int availableHour;
    private int charge;
    private int state;

    public RefundStrategyForm(int id, String name, int refundable, int availableHour, int charge, int state) {
        this.id = id;
        this.name = name;
        this.refundable = refundable;
        this.availableHour = availableHour;
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

    public int getavailableHour() {
        return availableHour;
    }

    public void setavailableHour(int availableHour) {
        this.availableHour = availableHour;
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
