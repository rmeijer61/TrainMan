package com.rmeijer.trainman;

import java.util.Date;
import java.util.UUID;

public class Payment {
    private UUID mId;
    private String mPayment;
    private Date mDate;

    public Payment() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getDesc() {
        return mPayment;
    }

    public void setDesc(String desc) {
        mPayment = desc;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

}

