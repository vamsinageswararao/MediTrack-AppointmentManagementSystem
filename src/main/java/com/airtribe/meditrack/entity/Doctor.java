package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interfaces.Searchable;

public class Doctor extends Person implements Searchable {
    private Specialization specialization;
    private double consultationFee;

    public Doctor(String id, String name, int age, String contact, 
                  Specialization specialization, double consultationFee) {
        super(id, name, age, contact);
        this.specialization = specialization;
        this.consultationFee = consultationFee;
    }

    // Getters
    public Specialization getSpecialization() { return specialization; }
    public double getConsultationFee() { return consultationFee; }

    // Setters
    public void setSpecialization(Specialization specialization) { 
        this.specialization = specialization;
        markAsUpdated();
    }
    
    public void setConsultationFee(double consultationFee) { 
        this.consultationFee = consultationFee;
        markAsUpdated();
    }

    @Override
    public boolean matches(String keyword) {
        return name.toLowerCase().contains(keyword.toLowerCase()) ||
               specialization.toString().toLowerCase().contains(keyword.toLowerCase()) ||
               id.equalsIgnoreCase(keyword);
    }

    @Override
    public String getEntityType() {
        return "Doctor";
    }

    @Override
    public boolean isValid() {
        return id != null && !id.isEmpty() &&
               name != null && !name.isEmpty() &&
               age > 0 && consultationFee > 0;
    }

    @Override
    public String toString() {
        return String.format("Doctor[ID=%s, Name=%s, Specialization=%s, Fee=%.2f]",
                           id, name, specialization, consultationFee);
    }

    // CSV representation
    public String toCSV() {
        return String.join(",", id, name, String.valueOf(age), contact, 
                          specialization.name(), String.valueOf(consultationFee));
    }

    public static Doctor fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 6) {
            return new Doctor(
                parts[0], parts[1], Integer.parseInt(parts[2]), parts[3],
                Specialization.valueOf(parts[4]), Double.parseDouble(parts[5])
            );
        }
        return null;
    }
}
