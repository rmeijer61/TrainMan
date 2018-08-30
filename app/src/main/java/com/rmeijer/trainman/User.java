package com.rmeijer.trainman;

import java.util.UUID;

public class User {
    private UUID mId;
    private String mName;

    public User() {
        mId = UUID.randomUUID();
        mName = "Testname";
    }

    public UUID getId() {
        return mId;
    }
    public String getName() {return mName; }
}