package nju.edu.cinema.po;

import java.util.Date;

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
}
