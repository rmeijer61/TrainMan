package com.rmeijer.trainman;

import java.util.UUID;

public class Picture {
    private UUID mId;

    public Picture() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }
}
