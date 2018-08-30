package com.rmeijer.trainman;

import java.util.UUID;

public class Menu {
    private UUID mId;

    public Menu() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }
}
