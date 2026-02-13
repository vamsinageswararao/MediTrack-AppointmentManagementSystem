package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interfaces.Searchable;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person implements Searchable, Cloneable {
    private String medicalHistory;
    private List<String> allergies; // For demonstrating deep copy

    public Patient(String id, String name, int age, String contact, String medicalHistory) {
        super(id, name, age, contact);
        this.medicalHistory = medicalHistory;
        this.allergies = new ArrayList<>();
    }

    // Getters
    public String getMedicalHistory() { return medicalHistory; }
    public List<String> getAllergies() { return new ArrayList<>(allergies); } // Defensive copy

    // Setters
    public void setMedicalHistory(String medicalHistory) { 
        this.medicalHistory = medicalHistory;
        markAsUpdated();
    }
    
    public void addAllergy(String allergy) {
        this.allergies.add(allergy);
        markAsUpdated();
    }
    
    public void setAllergies(List<String> allergies) {
        this.allergies = new ArrayList<>(allergies);
        markAsUpdated();
    }

    @Override
    public boolean matches(String keyword) {
        return name.toLowerCase().contains(keyword.toLowerCase()) ||
               id.equalsIgnoreCase(keyword) ||
               contact.contains(keyword);
    }

    @Override
    public String getEntityType() {
        return "Patient";
    }

    @Override
    public boolean isValid() {
        return id != null && !id.isEmpty() &&
               name != null && !name.isEmpty() &&
               age > 0;
    }

    // Deep copy implementation
    @Override
    public Patient clone() {
        try {
            Patient cloned = (Patient) super.clone();
            // Deep copy the allergies list
            cloned.allergies = new ArrayList<>(this.allergies);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }

    @Override
    public String toString() {
        return String.format("Patient[ID=%s, Name=%s, Age=%d, Contact=%s, Allergies=%d]",
                           id, name, age, contact, allergies.size());
    }

    // CSV representation
    public String toCSV() {
        String allergiesStr = String.join(";", allergies);
        return String.join(",", id, name, String.valueOf(age), contact, 
                          medicalHistory, allergiesStr);
    }

    public static Patient fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 5) {
            Patient patient = new Patient(
                parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], parts[4]
            );
            if (parts.length > 5 && !parts[5].isEmpty()) {
                String[] allergies = parts[5].split(";");
                for (String allergy : allergies) {
                    if (!allergy.isEmpty()) {
                        patient.addAllergy(allergy);
                    }
                }
            }
            return patient;
        }
        return null;
    }
}
