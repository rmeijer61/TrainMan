package com.rmeijer.trainman;

import android.util.Log;

import java.util.Date;
import java.util.UUID;

public class Customer {
    final String TAG = "Customer: ";
    private UUID mId;
    private Date mDate;
    private String mName;
    private String mGender;
    private Date mBirthDate;
    private String mPhone1;
    private String mPhone2;
    private String mEmail1;
    private String mEmail2;
    private String mAddress1;
    private String mAddress2;
    private String mCity;
    private String mState;
    private String mZip;
    private String mNote;

    public Customer() {
        this(UUID.randomUUID());
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

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getGender() {
        return mGender;
    }

    public void setBirthDate(Date birthDate) {
        mBirthDate = birthDate;
    }

    public Date getBirthDate() {
        return mBirthDate;
    }

    public String getPhone1() {
        return mPhone1;
    }

    public void setPhone1(String phone1) {
        this.mPhone1 = phone1;
    }

    public String getPhone2() {
        return mPhone2;
    }

    public void setPhone2(String phone2) {
        this.mPhone2 = phone2;
    }

    public String getEmail1() {
        return mEmail1;
    }

    public void setEmail1(String email1) {
        this.mEmail1 = email1;
    }

    public String getEmail2() {
        return mEmail2;
    }

    public void setEmail2(String email2) {
        this.mEmail2 = email2;
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

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    // 16.5 - Adding the filename-derived property
    public String getPhotoFilename() {
        Log.v( TAG, "Customer: " + "IMG_" + getId().toString() + ".jpg");
        return "IMG_" + getId().toString() + ".jpg";
    }

}
