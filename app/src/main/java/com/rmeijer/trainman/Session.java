package com.rmeijer.trainman;

import java.util.Date;
import java.util.UUID;

public class Session {
    private UUID mId;
    private UUID mCustomerId;
    private Date mDate;
    private String mService;
    private Date mSessionDate;
    private String mDescr;
    private boolean mCompleted;
    private boolean mPaid;
    private String mSign;

    public Session() {
        this(UUID.randomUUID());
    }

    public Session(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }


    public UUID getCustomerId() {
        return mCustomerId;
    }

    public void setCustomerId(UUID customerId) {
        mCustomerId = customerId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getService() {
        return mService;
    }

    public void setService(String service) {
        mService = service;
    }

    public String getDescr() {
        return mDescr;
    }

    public void setDescr(String descr) {
        mDescr = descr;
    }

    public Date getSessionDate() {
        return mSessionDate;
    }

    public void setSessionDate(Date date) {
        mSessionDate = date;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }

    public boolean isPaid() {
        return mPaid;
    }

    public void setPaid(boolean paid) {
        mPaid = paid;
    }

    public String getSign() {
        return mSign;
    }

    public void setSign(String sign) {
        mSign = sign;
    }
}

