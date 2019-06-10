package nju.edu.cinema.vo;

import nju.edu.cinema.po.Cumulative;

public class CumulativeVO {
    /**
     * 用户Id
     */
    private int userId;
    /**
     * 累计消费
     */
    private double cumulative;
    public CumulativeVO(Cumulative cumulative) {
    	this.userId=cumulative.getUserId();
    	this.cumulative=cumulative.getCumulative();
    }
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getCumulative() {
		return cumulative;
	}
	public void setCumulative(double cumulative) {
		this.cumulative = cumulative;
	}
    
}
