package nju.edu.cinema.vo;

import java.util.List;

public class OrderForm {

    private List<Integer> ticketId;

    private int couponId;

    public OrderForm(){

    }

    public int getCouponId() {
        return couponId;
    }

    public List<Integer> getTicketId() {
        return ticketId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public void setTicketId(List<Integer> ticketId) {
        this.ticketId = ticketId;
    }
}
