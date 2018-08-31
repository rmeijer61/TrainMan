package com.rmeijer.trainman;

import java.util.UUID;

// Future use:
// Create a menu object for each menu. Each menu has a set of buttons
// Each button has the same configuration

public class Menu {
    private UUID mId;

    public Menu() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }
}
