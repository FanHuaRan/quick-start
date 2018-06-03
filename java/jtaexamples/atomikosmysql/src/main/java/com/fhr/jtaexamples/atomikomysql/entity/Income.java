package com.fhr.jtaexamples.atomikomysql.entity;

import java.sql.Timestamp;

/**
 * @author FanHuaran
 * @description
 * @create 2018-05-29 13:38
 **/
public class Income {
    private long id;
    private long userId;
    private double amount;
    private Timestamp operateDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Timestamp operateDate) {
        this.operateDate = operateDate;
    }

}
