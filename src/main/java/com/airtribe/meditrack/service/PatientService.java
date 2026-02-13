package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.util.DataStore;
import java.util.List;
import java.util.stream.Collectors;

public class PatientService {
    private DataStore<Patient> patientStore;

    public PatientService() {
        this.patientStore = new DataStore<>();
    }

    public void addPatient(Patient patient) {
        patientStore.add(patient);
    }

    public Patient getPatientById(String id) {
        return patientStore.getById(id);
    }

    public List<Patient> getAllPatients() {
        return patientStore.getAll();
    }

    public List<Patient> searchPatient(String keyword) {
        return patientStore.getAll().stream()
                .filter(patient -> patient.matches(keyword))
                .collect(Collectors.toList());
    }

    public Patient searchPatient(String id, boolean exactMatch) {
        if (exactMatch) {
            return patientStore.getById(id);
        }
        return patientStore.getAll().stream()
                .filter(patient -> patient.getId().contains(id))
                .findFirst()
                .orElse(null);
    }

    public List<Patient> searchPatient(int minAge, int maxAge) {
        return patientStore.getAll().stream()
                .filter(patient -> patient.getAge() >= minAge && patient.getAge() <= maxAge)
                .collect(Collectors.toList());
    }


    public void updatePatient(Patient patient) {
        patientStore.update(patient);
    }

    public void deletePatient(String id) {
        patientStore.delete(id);
    }

    public int getPatientCount() {
        return patientStore.size();
    }
}
