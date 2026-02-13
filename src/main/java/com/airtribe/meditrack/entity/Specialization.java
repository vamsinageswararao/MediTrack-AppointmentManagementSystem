package com.airtribe.meditrack.entity;

public enum Specialization {
    GENERAL_PHYSICIAN("General Physician"),
    CARDIOLOGIST("Cardiologist"),
    DERMATOLOGIST("Dermatologist"),
    ORTHOPEDIC("Orthopedic"),
    OPHTHALMOLOGIST("Ophthalmologist"),
    PEDIATRICIAN("Pediatrician"),
    NEUROLOGIST("Neurologist"),
    PSYCHIATRIST("Psychiatrist"),
    DENTIST("Dentist"),
    ENT_SPECIALIST("ENT Specialist");

    private final String displayName;

    Specialization(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Specialization fromString(String text) {
        for (Specialization spec : Specialization.values()) {
            if (spec.displayName.equalsIgnoreCase(text) || spec.name().equalsIgnoreCase(text)) {
                return spec;
            }
        }
        return GENERAL_PHYSICIAN; // default
    }
}
