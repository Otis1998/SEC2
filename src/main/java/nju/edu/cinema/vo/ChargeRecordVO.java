package nju.edu.cinema.vo;

import nju.edu.cinema.po.ChargeRecord;

import java.sql.Timestamp;

public class ChargeRecordVO {
    /*
    * 充值时间
     */
    private Timestamp chargeTime;
    /*
    * 充值金额
     */
    private float chargeSum;
    /*
    * 赠送金额
     */
    private float bonusSum;
    /*
    * 余额
     */
    private float balance;

    public ChargeRecordVO(){

    }

    public ChargeRecordVO(ChargeRecord chargeRecord){
        this.chargeTime=chargeRecord.getChargeTime();
        this.chargeSum=chargeRecord.getChargeSum();
        this.bonusSum=chargeRecord.getBonusSum();
        this.balance=chargeRecord.getBalance();
    }

    public Timestamp getChargeTime() {
        return chargeTime;
    }

    public float getBalance() {
        return balance;
    }

    public float getBonusSum() {
        return bonusSum;
    }

    public float getChargeSum() {
        return chargeSum;
    }

    public void setChargeTime(Timestamp chargeTime) {
        this.chargeTime = chargeTime;
    }

    public void setChargeSum(float chargeSum) {
        this.chargeSum = chargeSum;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setBonusSum(float bonusSum) {
        this.bonusSum = bonusSum;
    }
}
