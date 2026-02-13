package com.airtribe.meditrack.entity;

public enum AppointmentStatus {
    SCHEDULED("Scheduled"),
    CONFIRMED("Confirmed"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    PENDING("Pending"),
    NO_SHOW("No Show");

    private final String displayName;

    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static AppointmentStatus fromString(String text) {
        for (AppointmentStatus status : AppointmentStatus.values()) {
            if (status.displayName.equalsIgnoreCase(text) || status.name().equalsIgnoreCase(text)) {
                return status;
            }
        }
        return PENDING;
    }
}
