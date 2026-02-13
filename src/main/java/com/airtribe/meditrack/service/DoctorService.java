package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.util.DataStore;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Optional;

public class DoctorService {
    private DataStore<Doctor> doctorStore;

    public DoctorService() {
        this.doctorStore = new DataStore<>();
    }

    public void addDoctor(Doctor doctor) {
        doctorStore.add(doctor);
    }

    public Doctor getDoctorById(String id) {
        return doctorStore.getById(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }

    public List<Doctor> searchDoctors(String keyword) {
        return doctorStore.getAll().stream()
                .filter(doctor -> doctor.matches(keyword))
                .collect(Collectors.toList());
    }

    // Stream & Lambda : Filter doctors by specialization
    public List<Doctor> getDoctorsBySpecialization(Specialization specialization) {
        return doctorStore.getAll().stream()
                .filter(doctor -> doctor.getSpecialization() == specialization)
                .sorted(Comparator.comparing(Doctor::getName))
                .collect(Collectors.toList());
    }


//    Stream & Lambda : Calculate average consultation fee
    public double getAverageConsultationFee() {
        return doctorStore.getAll().stream()
                .mapToDouble(Doctor::getConsultationFee)
                .average()
                .orElse(0.0);
    }


    public void updateDoctor(Doctor doctor) {
        doctorStore.update(doctor);
    }

    public void deleteDoctor(String id) {
        doctorStore.delete(id);
    }

    public int getDoctorCount() {
        return doctorStore.size();
    }
}
