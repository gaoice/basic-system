package com.gaoice.system.user.enumeration;

public enum Status {

    ENABLED(1), DISABLED(-1);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
