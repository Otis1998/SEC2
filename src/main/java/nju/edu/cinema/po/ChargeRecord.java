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
    private float chargeSum;
    /*
    * 优惠金额
     */
    private float bonusSum;
    /*
    * 余额
     */
    private float balance;

    public ChargeRecord(){

    }

    public float getChargeSum() {
        return chargeSum;
    }

    public float getBonusSum() {
        return bonusSum;
    }

    public int getId() {
        return id;
    }

    public float getBalance() {
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

    public void setBonusSum(float bonusSum) {
        this.bonusSum = bonusSum;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setChargeSum(float chargeSum) {
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
