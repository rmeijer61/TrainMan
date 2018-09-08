package com.rmeijer.trainman;

import java.util.Date;
import java.util.UUID;

public class Customer {
    private UUID mId;
    private Date mDate;
    private String mName;
    private char mGender;
    private Date mBirthDate;

    public Customer() {
        // 14.15 - Adding Customer constructor
        //mId = UUID.randomUUID();
        //mDate = new Date();
        this(UUID.randomUUID());

        // Just for development
        mBirthDate = new Date();
    }

    // 14.15 - Adding Customer constructor
    public Customer(UUID id) {
        mId = id;
        mDate = new Date();
    }
    // end 14.15

    public UUID getId() {
        return mId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setGender(char gender) {
        mGender = gender;
    }

    public char getGender() {
        return mGender;
    }

    public void setBirthDate(Date birthDate) {
        mBirthDate = birthDate;
    }

    public Date getBirthDate() {
        return mBirthDate;
    }
}
