package nju.edu.cinema.po;


import java.sql.Timestamp;

/**
 * Created by liying on 2019/4/14.
 */

public class VIPCard {
	/**
	 * 会员卡价格
	 */
    private final static double price = 25;
    /**
     * 优惠描述
     */
    private String description;
    private int full;
    private int present;
    /**
     * 优惠id
     */
    private int descriptionId;
    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡id
     */
    private int id;

    /**
     * 会员卡余额
     */
    private double balance;

    /**
     * 办卡日期
     */
    private Timestamp joinDate;


    public VIPCard() {

    }

    public double getPrice() {
    	return price;
    }
    public String getDescription() {
    	return description;
    }
    public void setDescription(String description) {
		this.description=description;
	}
    public int getFull() {
    	return full;
    }
    public void setFull(int full) {
    	this.full=full;
    }
    public int getPresent() {
    	return present;
    }
    public void setPresent(int present) {
		this.present=present;
	}
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    public double calculate(double amount) {
        return (int)(amount/200)*30+amount;

    }

	public int getDescriptionId() {
		return descriptionId;
	}

	public void setDescriptionId(int descriptionId) {
		this.descriptionId = descriptionId;
	}
}
