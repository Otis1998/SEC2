package nju.edu.cinema.vo;

import java.util.Date;
import java.util.List;

public class OrderVO {
    /*
    * 订单id
     */
    private int orderId;
    /**
     * 电影名
     */
    private String movieName;
    /**
     * 海报URL
     */
    private String posterURL;
    /**
     * 影厅ID
     */
    private int hallId;
    /**
     * 座位列表
     */
    private List<SeatForm> seatFormList;
    /**
     * 票的张数
     */
    private int numOfTicket;
    /**
     * 电影开始放映时间
     */
    private Date startTime;
    /**
     * 电影结束放映时间
     */
    private Date endTime;
    /**
     * 订单优惠后的支付金额
     */
    private double cost;
    /**
     * 订单状态
     * 0: 可退  1：不可退
     */
    private int state;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName){
        this.movieName = movieName;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL){
        this.posterURL = posterURL;
    }

    public int getHallId(){
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public List<SeatForm> getSeatFormList() {
        return seatFormList;
    }

    public void setSeatFormList(List<SeatForm> seatFormList) {
        this.seatFormList = seatFormList;
    }

    public int getNumOfTicket() {
        return numOfTicket;
    }

    public void setNumOfTicket(int numOfTicket) {
        this.numOfTicket = numOfTicket;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
