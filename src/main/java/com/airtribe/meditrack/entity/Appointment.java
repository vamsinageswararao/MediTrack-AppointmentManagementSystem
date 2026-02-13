package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Appointment extends MedicalEntity implements Cloneable {
    private String patientId;
    private String doctorId;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;
    private Map<String, String> notes; // For demonstrating deep copy

    public Appointment(String appointmentId, String patientId, String doctorId, 
                       LocalDateTime appointmentTime, AppointmentStatus status) {
        super(appointmentId);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.notes = new HashMap<>();
    }

    // Getters
    public String getAppointmentId() { return id; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public AppointmentStatus getStatus() { return status; }
    public Map<String, String> getNotes() { return new HashMap<>(notes); } // Defensive copy

    // Setters
    public void setAppointmentTime(LocalDateTime appointmentTime) { 
        this.appointmentTime = appointmentTime;
        markAsUpdated();
    }
    
    public void setStatus(AppointmentStatus status) { 
        this.status = status;
        markAsUpdated();
    }
    
    public void addNote(String key, String value) {
        this.notes.put(key, value);
        markAsUpdated();
    }

    @Override
    public String getEntityType() {
        return "Appointment";
    }

    @Override
    public boolean isValid() {
        return id != null && !id.isEmpty() &&
               patientId != null && !patientId.isEmpty() &&
               doctorId != null && !doctorId.isEmpty() &&
               appointmentTime != null;
    }

    // Deep copy implementation
    @Override
    public Appointment clone() {
        try {
            Appointment cloned = (Appointment) super.clone();
            // Deep copy the notes map
            cloned.notes = new HashMap<>(this.notes);
            // LocalDateTime is immutable, so no need to clone
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }

    @Override
    public String toString() {
        return String.format("Appointment[ID=%s, Patient=%s, Doctor=%s, Time=%s, Status=%s]",
                           id, patientId, doctorId, appointmentTime, status);
    }

    // CSV representation
    public String toCSV() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return String.join(",", id, patientId, doctorId, 
                          appointmentTime.format(formatter), status.name());
    }

    public static Appointment fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 5) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return new Appointment(
                parts[0], parts[1], parts[2],
                LocalDateTime.parse(parts[3], formatter),
                AppointmentStatus.valueOf(parts[4])
            );
        }
        return null;
    }
}
