package nju.edu.cinema.po;

public class Cumulative {
    /**
     * 用户Id
     */
    private int userId;
    /**
     * 累计消费
     */
    private double cumulative;
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
