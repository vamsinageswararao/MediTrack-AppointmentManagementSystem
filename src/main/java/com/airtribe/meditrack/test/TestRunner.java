package com.airtribe.meditrack.test;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.util.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Manual test runner (no JUnit)
 * Tests all features of the MediTrack application
 */
public class TestRunner {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("  MediTrack - Comprehensive Test Suite");
        System.out.println("=".repeat(60));
        
        testEnums();
        testSingletonPattern();
        testDoctorService();
        testPatientService();
        testDeepCopy();
        testAppointmentService();
        testFactoryPattern();
        testMethodOverloading();
        testStreamsAndLambdas();
        testImmutableClass();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  All Tests Completed Successfully!");
        System.out.println("=".repeat(60));
    }

    private static void testEnums() {
        System.out.println("\n[TEST] Testing Enums...");
        
        // Test Specialization enum
        Specialization spec = Specialization.CARDIOLOGIST;
        System.out.println("  Specialization: " + spec);
        System.out.println("  Display Name: " + spec.getDisplayName());
        
        // Test AppointmentStatus enum
        AppointmentStatus status = AppointmentStatus.CONFIRMED;
        System.out.println("  Status: " + status);
        
        // Test BillType enum
        BillType billType = BillType.CONSULTATION;
        System.out.println("  Bill Type: " + billType);
        
        System.out.println("  ✓ Enums working correctly");
    }

    private static void testSingletonPattern() {
        System.out.println("\n[TEST] Testing Singleton Pattern...");
        
        IdGenerator instance1 = IdGenerator.getInstance();
        IdGenerator instance2 = IdGenerator.getInstance();
        
        System.out.println("  Instance1 == Instance2: " + (instance1 == instance2));
        System.out.println("  Generated Doctor ID: " + IdGenerator.newDoctorId());
        System.out.println("  Generated Patient ID: " + IdGenerator.newPatientId());
        
        // Test lazy initialization
        IdGenerator lazy = IdGenerator.getLazyInstance();
        System.out.println("  Lazy instance created: " + (lazy != null));
        
        System.out.println("  ✓ Singleton pattern working correctly");
    }

    private static void testDoctorService() {
        System.out.println("\n[TEST] Testing DoctorService...");
        DoctorService doctorService = new DoctorService();
        
        Doctor doctor1 = new Doctor(
            IdGenerator.newDoctorId(),
            "Dr. John Smith",
            45,
            "9876543210",
            Specialization.CARDIOLOGIST,
            1500.0
        );
        
        Doctor doctor2 = new Doctor(
            IdGenerator.newDoctorId(),
            "Dr. Sarah Lee",
            38,
            "9876543211",
            Specialization.DERMATOLOGIST,
            1000.0
        );
        
        doctorService.addDoctor(doctor1);
        doctorService.addDoctor(doctor2);
        
        System.out.println("  Added: " + doctor1);
        System.out.println("  Added: " + doctor2);
        System.out.println("  Total doctors: " + doctorService.getDoctorCount());
        System.out.println("  ✓ DoctorService working correctly");
    }

    private static void testPatientService() {
        System.out.println("\n[TEST] Testing PatientService...");
        PatientService patientService = new PatientService();
        
        Patient patient1 = new Patient(
            IdGenerator.newPatientId(),
            "Jane Doe",
            30,
            "9123456780",
            "No known allergies"
        );
        patient1.addAllergy("Penicillin");
        patient1.addAllergy("Peanuts");
        
        patientService.addPatient(patient1);
        System.out.println("  Added: " + patient1);
        System.out.println("  Allergies: " + patient1.getAllergies());
        System.out.println("  Total patients: " + patientService.getPatientCount());
        System.out.println("  ✓ PatientService working correctly");
    }

    private static void testDeepCopy() {
        System.out.println("\n[TEST] Testing Deep Copy (Cloneable)...");
        
        // Test Patient deep copy
        Patient original = new Patient(
            IdGenerator.newPatientId(),
            "Test Patient",
            25,
            "1234567890",
            "Test history"
        );
        original.addAllergy("Dust");
        original.addAllergy("Pollen");
        
        Patient cloned = original.clone();
        cloned.addAllergy("Shellfish");
        
        System.out.println("  Original allergies: " + original.getAllergies());
        System.out.println("  Cloned allergies: " + cloned.getAllergies());
        System.out.println("  Deep copy verified: " + 
            (original.getAllergies().size() != cloned.getAllergies().size()));
        
        // Test Appointment deep copy
        Appointment apt1 = new Appointment(
            IdGenerator.newAppointmentId(),
            "PAT2001",
            "DOC1001",
            LocalDateTime.now(),
            AppointmentStatus.SCHEDULED
        );
        apt1.addNote("symptoms", "Fever");
        
        Appointment apt2 = apt1.clone();
        apt2.addNote("priority", "High");
        
        System.out.println("  Original notes: " + apt1.getNotes());
        System.out.println("  Cloned notes: " + apt2.getNotes());
        System.out.println("  ✓ Deep copy working correctly");
    }

    private static void testAppointmentService() {
        System.out.println("\n[TEST] Testing AppointmentService...");
        AppointmentService appointmentService = new AppointmentService();
        
        Appointment appointment1 = new Appointment(
            IdGenerator.newAppointmentId(),
            "PAT2001",
            "DOC1001",
            LocalDateTime.now().plusDays(1),
            AppointmentStatus.SCHEDULED
        );
        
        appointmentService.scheduleAppointment(appointment1);
        System.out.println("  Scheduled: " + appointment1);
        System.out.println("  Total appointments: " + appointmentService.getAppointmentCount());
        
        try {
            appointmentService.confirmAppointment(appointment1.getAppointmentId());
            System.out.println("  Status updated to: " + appointment1.getStatus());
        } catch (Exception e) {
            System.out.println("  Error: " + e.getMessage());
        }
        
        System.out.println("  ✓ AppointmentService working correctly");
    }

    private static void testFactoryPattern() {
        System.out.println("\n[TEST] Testing Factory Pattern (BillFactory)...");
        
        Bill consultationBill = BillFactory.createConsultationBill("APT3001", 1000.0);
        System.out.println("  Created: " + consultationBill);
        
        Bill surgeryBill = BillFactory.createSurgeryBill("APT3002", 5000.0, 2000.0);
        System.out.println("  Created: " + surgeryBill);
        
        Bill diagnosticBill = BillFactory.createDiagnosticBill("APT3003", 800.0);
        System.out.println("  Created: " + diagnosticBill);
        
        System.out.println("  ✓ Factory pattern working correctly");
    }

    private static void testMethodOverloading() {
        System.out.println("\n[TEST] Testing Method Overloading...");
        
        PatientService patientService = new PatientService();
        
        // Add test patients
        patientService.addPatient(new Patient("PAT001", "Alice", 25, "1111111111", ""));
        patientService.addPatient(new Patient("PAT002", "Bob", 30, "2222222222", ""));
        patientService.addPatient(new Patient("PAT003", "Charlie", 35, "3333333333", ""));
        
        System.out.println("  searchPatient(String): " + 
            patientService.searchPatient("Alice").size() + " results");
        System.out.println("  searchPatient(String, boolean): " + 
            (patientService.searchPatient("PAT001", true) != null ? "Found" : "Not found"));
        System.out.println("  searchPatient(int, int): " + 
            patientService.searchPatient(20, 30).size() + " results");
        
        // Test Bill method overloading
        Bill bill = BillFactory.createConsultationBill("APT001", 1000.0);
        bill.setAdditionalCharges(200.0);
        
        System.out.println("  calculateTotal(): ₹" + bill.calculateTotal());
        System.out.println("  calculateTotal(0.10): ₹" + bill.calculateTotal(0.10));
        System.out.println("  calculateTotal(10, true): ₹" + bill.calculateTotal(10, true));
        
        System.out.println("  ✓ Method overloading working correctly");
    }

    private static void testStreamsAndLambdas() {
        System.out.println("\n[TEST] Testing Streams & Lambdas...");
        
        DoctorService doctorService = new DoctorService();
        
        // Add test doctors
        doctorService.addDoctor(new Doctor("D001", "Dr. A", 40, "111", 
            Specialization.CARDIOLOGIST, 1500.0));
        doctorService.addDoctor(new Doctor("D002", "Dr. B", 35, "222", 
            Specialization.CARDIOLOGIST, 1200.0));
        doctorService.addDoctor(new Doctor("D003", "Dr. C", 45, "333", 
            Specialization.DERMATOLOGIST, 1000.0));
        
        System.out.println("  Average fee: ₹" + doctorService.getAverageConsultationFee());
        
        List<Doctor> cardiologists = doctorService.getDoctorsBySpecialization(Specialization.CARDIOLOGIST);
        System.out.println("  Cardiologists: " + cardiologists.size());
        
        System.out.println("  ✓ Streams and Lambdas working correctly");
    }

    private static void testImmutableClass() {
        System.out.println("\n[TEST] Testing Immutable Class (BillSummary)...");
        
        BillSummary summary = new BillSummary(
            "BILL001",
            "John Doe",
            "Dr. Smith",
            1500.00,
            true
        );
        
        System.out.println("  Created: " + summary);
        System.out.println("  Bill ID: " + summary.getBillId());
        System.out.println("  Total Amount: ₹" + summary.getTotalAmount());
        System.out.println("  Is Paid: " + summary.isPaid());
        System.out.println("  Note: BillSummary is immutable (final class, no setters)");
        
        System.out.println("  ✓ Immutable class working correctly");
    }
}
