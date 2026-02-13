package com.airtribe.meditrack.entity;


public enum BillType {
    CONSULTATION("Consultation Bill"),
    SURGERY("Surgery Bill"),
    DIAGNOSTIC("Diagnostic Bill"),
    PHARMACY("Pharmacy Bill"),
    EMERGENCY("Emergency Bill");

    private final String displayName;

    BillType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
