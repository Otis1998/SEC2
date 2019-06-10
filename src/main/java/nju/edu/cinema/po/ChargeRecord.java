package nju.edu.cinema.po;

import java.sql.Timestamp;

public class ChargeRecord {
    /*
    * 充值记录id
     */
    private int id;
    /*
    * 用户id
     */
    private int userId;
    /*
    * 会员id
     */
    private int vipId;
    /*
    * 充值时间
     */
    private Timestamp chargeTime;
    /*
    * 充值金额
     */
    private double chargeSum;
    /*
    * 优惠金额
     */
    private double bonusSum;
    /*
    * 余额
     */
    private double balance;

    public ChargeRecord(){

    }

    public double getChargeSum() {
        return chargeSum;
    }

    public double getBonusSum() {
        return bonusSum;
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public int getUserId() {
        return userId;
    }

    public int getVipId() {
        return vipId;
    }

    public Timestamp getChargeTime() {
        return chargeTime;
    }

    public void setBonusSum(double d) {
        this.bonusSum = d;
    }

    public void setBalance(double d) {
        this.balance = d;
    }

    public void setChargeSum(double chargeSum) {
        this.chargeSum = chargeSum;
    }

    public void setChargeTime(Timestamp chargeTime) {
        this.chargeTime = chargeTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setVipId(int vipId) {
        this.vipId = vipId;
    }
}
