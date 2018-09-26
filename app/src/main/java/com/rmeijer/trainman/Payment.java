package com.rmeijer.trainman;

import java.util.Date;
import java.util.UUID;

public class Payment {
    private UUID mId;
    private Date mDate;
    private UUID mCustomerId;
    private UUID mSessionId;
    private String mPayMethod;
    private String mCardNumber;
    private Date mExpireDate;
    private String mSecurityCode;
    private double mAmount;
    private Date mPayDate;
    private String mName;
    private String mAddress1;
    private String mAddress2;
    private String mCity;
    private String mState;
    private String mZip;
    
    public Payment() {
        this(UUID.randomUUID());
    }

    public Payment(UUID id) {
        mId = id;
        mDate = new Date();
        mExpireDate = new Date(0);
        mPayDate = new Date(0);
    }

    public UUID getId() {
        return mId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public UUID getCustomerId() {
        return mCustomerId;
    }

    public void setCustomerId(UUID customerId) {
        mCustomerId = customerId;
    }

    public UUID getSessionId() {
        return mSessionId;
    }

    public void setSessionId(UUID sessionId) {
        mSessionId = sessionId;
    }

    public void setPayMethod(String payMethod) {
        mPayMethod = payMethod;
    }

    public String getPayMethod() {
        return mPayMethod;
    }

    public void setExpireDate(Date expireDate) {
        mExpireDate = expireDate;
    }

    public Date getExpireDate() {
        return mExpireDate;
    }

    public String getSecurityCode() {
        return mSecurityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.mSecurityCode = securityCode;
    }

    public String getCardNumber() {
        return mCardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.mCardNumber = cardNumber;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double amount) {
        this.mAmount = amount;
    }

    public Date getPayDate() {
        return mPayDate;
    }

    public void setPayDate(Date payDate) {
        this.mPayDate = payDate;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress1() {
        return mAddress1;
    }

    public void setAddress1(String address1) {
        this.mAddress1 = address1;
    }

    public String getAddress2() {
        return mAddress2;
    }

    public void setAddress2(String address2) {
        this.mAddress2 = address2;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        this.mState = state;
    }

    public String getZip() {
        return mZip;
    }

    public void setZip(String zip) {
        this.mZip = zip;
    }

}
