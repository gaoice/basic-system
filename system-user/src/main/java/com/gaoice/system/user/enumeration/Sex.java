package com.gaoice.system.user.enumeration;

public enum Sex {

    MAN(1), WOMAN(0), UNKNOWN(2);

    private int value;

    Sex(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
