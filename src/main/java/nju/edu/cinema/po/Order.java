package nju.edu.cinema.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * created by Wang Youxin on 2019/06/08
 */
public class Order {
    /**
     * 订单Id
     */
    private int orderId;
    /**
     * 用户Id
     */
    private int userId;
    /**
     * 电影Id
     */
    private int movieId;
    /**
     * 订单消费金额
     */
    private double cost;
    /**
     * 订单生成时间
     */
    private Date time;

    /**
     * 对应ticket的Id，用&连接
     */
    private String ticketsId;
    /**
     *支付方式
     * 0：银行卡支付 1：会员卡支付
     */
    private int paymentMode;
    /**
     * 订单是否可退的状态
     * 0: 可退  1：不可退
     */
    private int state;

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getTicketsId() {
        return ticketsId;
    }

    public void setTicketsId(String ticketsId) {
        this.ticketsId = ticketsId;
    }

    public int getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Integer> getTicketsIdList(){
        List<Integer> ticketsIdList = new ArrayList<>();
        String[] ticketsId = this.getTicketsId().split("&");
        for(String s:ticketsId){
            ticketsIdList.add(Integer.parseInt(s));
        }
        return ticketsIdList;
    }
}
