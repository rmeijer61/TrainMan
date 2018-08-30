package com.rmeijer.trainman;

import java.util.Date;
import java.util.UUID;

public class Customer {
    private UUID mId;
    private String mName;
    private Date mDate;

    public Customer() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
