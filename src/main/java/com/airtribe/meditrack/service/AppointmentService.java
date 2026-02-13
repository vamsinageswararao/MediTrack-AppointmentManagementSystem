package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppointmentService {
    private DataStore<Appointment> appointmentStore;

    public AppointmentService() {
        this.appointmentStore = new DataStore<>();
    }

    public void scheduleAppointment(Appointment appointment) {
        appointmentStore.add(appointment);
    }

    public Appointment getAppointmentById(String id) throws AppointmentNotFoundException {
        Appointment appointment = appointmentStore.getById(id);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment not found with ID: " + id);
        }
        return appointment;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentStore.getAll();
    }

    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointmentStore.getAll().stream()
                .filter(apt -> apt.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointmentStore.getAll().stream()
                .filter(apt -> apt.getDoctorId().equals(doctorId))
                .collect(Collectors.toList());
    }


//    Stream Analytics : Count appointments per doctor
    public Map<String, Long> getAppointmentsPerDoctor() {
        return appointmentStore.getAll().stream()
                .collect(Collectors.groupingBy(
                    Appointment::getDoctorId,
                    Collectors.counting()
                ));
    }

    public void updateAppointment(Appointment appointment) {
        appointmentStore.update(appointment);
    }

    public void cancelAppointment(String id) throws AppointmentNotFoundException {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentStore.update(appointment);
    }

    public void completeAppointment(String id) throws AppointmentNotFoundException {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentStore.update(appointment);
    }

    public void confirmAppointment(String id) throws AppointmentNotFoundException {
        Appointment appointment = getAppointmentById(id);
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentStore.update(appointment);
    }

    public int getAppointmentCount() {
        return appointmentStore.size();
    }
}
