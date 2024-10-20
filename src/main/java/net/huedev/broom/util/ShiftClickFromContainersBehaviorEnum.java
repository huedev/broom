package net.huedev.broom.util;

public enum ShiftClickFromContainersBehaviorEnum {
    VANILLA("Vanilla"),
    ALWAYS_LAST_SLOT("Always Go to Last Slot");

    final String stringValue;

    ShiftClickFromContainersBehaviorEnum(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
