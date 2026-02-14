package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.util.*;
import com.airtribe.meditrack.exception.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main entry point for the MediTrack application
 * Console-based menu-driven UI
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DoctorService doctorService = new DoctorService();
    private static final PatientService patientService = new PatientService();
    private static final AppointmentService appointmentService = new AppointmentService();
    private static final BillService billService = new BillService();
    private static boolean loadData = false;

    public static void main(String[] args) {
        // Check for command-line arguments
        if (args.length > 0 && args[0].equals("--loadData")) {
            loadData = true;
            System.out.println("Loading data from CSV files...");
            loadDataFromCSV();
        }

        printWelcomeBanner();
        loadSampleData();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    doctorMenu();
                    break;
                case 2:
                    patientMenu();
                    break;
                case 3:
                    appointmentMenu();
                    break;
                case 4:
                    billMenu();
                    break;
                case 5:
                    analyticsMenu();
                    break;
                case 6:
                    saveDataToCSV();
                    break;
                case 0:
                    running = false;
                    System.out.println("\n" + "=".repeat(50));
                    System.out.println("Thank you for using " + Constants.APP_NAME + "!");
                    System.out.println("=".repeat(50));
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }

        scanner.close();
    }

    private static void printWelcomeBanner() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  " + Constants.APP_NAME + " - Healthcare Management System");
        System.out.println("  Version: " + Constants.APP_VERSION);
        System.out.println("=".repeat(60));
    }

    private static void printMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Doctor Management");
        System.out.println("2. Patient Management");
        System.out.println("3. Appointment Management");
        System.out.println("4. Billing Management");
        System.out.println("5. Analytics & Reports");
        System.out.println("6. Save Data to CSV");
        System.out.println("0. Exit");
        System.out.println("=".repeat(50));
    }

    // ==================== DOCTOR MENU ====================
    private static void doctorMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- DOCTOR MANAGEMENT ---");
            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Search Doctor");
            System.out.println("4. View Doctors by Specialization");
            System.out.println("5. Update Doctor");
            System.out.println("6. Delete Doctor");
            System.out.println("0. Back to Main Menu");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: addDoctor(); break;
                case 2: viewAllDoctors(); break;
                case 3: searchDoctor(); break;
                case 4: viewDoctorsBySpecialization(); break;
                case 5: updateDoctor(); break;
                case 6: deleteDoctor(); break;
                case 0: back = true; break;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void addDoctor() {
        System.out.println("\n--- ADD NEW DOCTOR ---");
        String name = getStringInput("Enter doctor name: ");
        int age = getIntInput("Enter age: ");
        String contact = getStringInput("Enter contact number (10 digits): ");

        System.out.println("\nSelect Specialization:");
        Specialization[] specs = Specialization.values();
        for (int i = 0; i < specs.length; i++) {
            System.out.println((i + 1) + ". " + specs[i]);
        }
        int specChoice = getIntInput("Enter choice: ") - 1;
        Specialization specialization = (specChoice >= 0 && specChoice < specs.length)
                ? specs[specChoice] : Specialization.GENERAL_PHYSICIAN;

        double fee = getDoubleInput("Enter consultation fee: ");

        String id = IdGenerator.newDoctorId();
        Doctor doctor = new Doctor(id, name, age, contact, specialization, fee);
        doctorService.addDoctor(doctor);

        System.out.println("\n✓ Doctor added successfully!");
        System.out.println(doctor);
    }

    private static void viewAllDoctors() {
        System.out.println("\n--- ALL DOCTORS ---");
        List<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors found!");
        } else {
            System.out.printf("%-12s %-20s %-15s %-20s %-12s%n",
                    "ID", "Name", "Age", "Specialization", "Fee");
            System.out.println("-".repeat(85));
            for (Doctor doctor : doctors) {
                System.out.printf("%-12s %-20s %-15d %-20s ₹%-11.2f%n",
                        doctor.getId(), doctor.getName(), doctor.getAge(),
                        doctor.getSpecialization(), doctor.getConsultationFee());
            }
        }
    }

    private static void searchDoctor() {
        String keyword = getStringInput("Enter search keyword (name/specialization): ");
        List<Doctor> doctors = doctorService.searchDoctors(keyword);

        if (doctors.isEmpty()) {
            System.out.println("No doctors found matching: " + keyword);
        } else {
            System.out.println("\n--- SEARCH RESULTS ---");
            for (Doctor doctor : doctors) {
                System.out.println(doctor);
            }
        }
    }

    private static void viewDoctorsBySpecialization() {
        System.out.println("\nSelect Specialization:");
        Specialization[] specs = Specialization.values();
        for (int i = 0; i < specs.length; i++) {
            System.out.println((i + 1) + ". " + specs[i]);
        }
        int choice = getIntInput("Enter choice: ") - 1;

        if (choice >= 0 && choice < specs.length) {
            Specialization spec = specs[choice];
            List<Doctor> doctors = doctorService.getDoctorsBySpecialization(spec);

            System.out.println("\n--- Doctors in " + spec + " ---");
            if (doctors.isEmpty()) {
                System.out.println("No doctors found for this specialization!");
            } else {
                for (Doctor doctor : doctors) {
                    System.out.println(doctor);
                }
            }
        }
    }

    private static void updateDoctor() {
        String id = getStringInput("Enter doctor ID to update: ");
        Doctor doctor = doctorService.getDoctorById(id);

        if (doctor == null) {
            System.out.println("Doctor not found!");
            return;
        }

        System.out.println("Current details: " + doctor);
        double newFee = getDoubleInput("Enter new consultation fee (current: " + doctor.getConsultationFee() + "): ");
        doctor.setConsultationFee(newFee);
        doctorService.updateDoctor(doctor);

        System.out.println("✓ Doctor updated successfully!");
    }

    private static void deleteDoctor() {
        String id = getStringInput("Enter doctor ID to delete: ");
        Doctor doctor = doctorService.getDoctorById(id);

        if (doctor == null) {
            System.out.println("Doctor not found!");
            return;
        }

        System.out.println("Are you sure you want to delete: " + doctor.getName() + "? (yes/no)");
        String confirm = getStringInput("");

        if (confirm.equalsIgnoreCase("yes")) {
            doctorService.deleteDoctor(id);
            System.out.println("✓ Doctor deleted successfully!");
        }
    }

    // ==================== PATIENT MENU ====================
    private static void patientMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- PATIENT MANAGEMENT ---");
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient (by keyword)");
            System.out.println("4. Search Patient (by ID)");
            System.out.println("5. Search Patient (by age range)");
            System.out.println("6. Demonstrate Deep Copy");
            System.out.println("7. Update Patient");
            System.out.println("8. Delete Patient");
            System.out.println("0. Back to Main Menu");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: addPatient(); break;
                case 2: viewAllPatients(); break;
                case 3: searchPatientByKeyword(); break;
                case 4: searchPatientById(); break;
                case 5: searchPatientByAge(); break;
                case 6: demonstrateDeepCopy(); break;
                case 7: updatePatient(); break;
                case 8: deletePatient(); break;
                case 0: back = true; break;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void addPatient() {
        System.out.println("\n--- ADD NEW PATIENT ---");
        String name = getStringInput("Enter patient name: ");
        int age = getIntInput("Enter age: ");
        String contact = getStringInput("Enter contact number: ");
        String medicalHistory = getStringInput("Enter medical history: ");

        String id = IdGenerator.newPatientId();
        Patient patient = new Patient(id, name, age, contact, medicalHistory);

        System.out.println("Add allergies? (yes/no)");
        if (getStringInput("").equalsIgnoreCase("yes")) {
            boolean addingAllergies = true;
            while (addingAllergies) {
                String allergy = getStringInput("Enter allergy (or 'done' to finish): ");
                if (allergy.equalsIgnoreCase("done")) {
                    addingAllergies = false;
                } else {
                    patient.addAllergy(allergy);
                }
            }
        }

        patientService.addPatient(patient);
        System.out.println("\n✓ Patient added successfully!");
        System.out.println(patient);
    }

    private static void viewAllPatients() {
        System.out.println("\n--- ALL PATIENTS ---");
        List<Patient> patients = patientService.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found!");
        } else {
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
    }

    private static void searchPatientByKeyword() {
        String keyword = getStringInput("Enter search keyword: ");
        List<Patient> patients = patientService.searchPatient(keyword);

        if (patients.isEmpty()) {
            System.out.println("No patients found!");
        } else {
            System.out.println("\n--- SEARCH RESULTS ---");
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
    }

    private static void searchPatientById() {
        String id = getStringInput("Enter patient ID: ");
        Patient patient = patientService.searchPatient(id, true);

        if (patient == null) {
            System.out.println("Patient not found!");
        } else {
            System.out.println(patient);
        }
    }

    private static void searchPatientByAge() {
        int minAge = getIntInput("Enter minimum age: ");
        int maxAge = getIntInput("Enter maximum age: ");
        List<Patient> patients = patientService.searchPatient(minAge, maxAge);

        if (patients.isEmpty()) {
            System.out.println("No patients found in this age range!");
        } else {
            System.out.println("\n--- PATIENTS (Age " + minAge + "-" + maxAge + ") ---");
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
    }

    private static void demonstrateDeepCopy() {
        System.out.println("\n--- DEEP COPY DEMONSTRATION ---");

        // Create a patient with allergies
        Patient original = new Patient(IdGenerator.newPatientId(), "Test Patient", 30, "1234567890", "No history");
        original.addAllergy("Penicillin");
        original.addAllergy("Peanuts");

        // Clone the patient (deep copy)
        Patient cloned = original.clone();

        System.out.println("Original: " + original);
        System.out.println("Cloned: " + cloned);

        // Modify the clone's allergies
        cloned.addAllergy("Dust");

        System.out.println("\nAfter adding allergy to clone:");
        System.out.println("Original allergies: " + original.getAllergies());
        System.out.println("Cloned allergies: " + cloned.getAllergies());
        System.out.println("\n✓ Deep copy successful - original unchanged!");
    }

    private static void updatePatient() {
        String id = getStringInput("Enter patient ID to update: ");
        Patient patient = patientService.getPatientById(id);

        if (patient == null) {
            System.out.println("Patient not found!");
            return;
        }

        System.out.println("Current details: " + patient);
        String newHistory = getStringInput("Enter updated medical history: ");
        patient.setMedicalHistory(newHistory);
        patientService.updatePatient(patient);

        System.out.println("✓ Patient updated successfully!");
    }

    private static void deletePatient() {
        String id = getStringInput("Enter patient ID to delete: ");
        Patient patient = patientService.getPatientById(id);

        if (patient == null) {
            System.out.println("Patient not found!");
            return;
        }

        System.out.println("Delete " + patient.getName() + "? (yes/no)");
        if (getStringInput("").equalsIgnoreCase("yes")) {
            patientService.deletePatient(id);
            System.out.println("✓ Patient deleted!");
        }
    }

    // ==================== APPOINTMENT MENU ====================
    private static void appointmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- APPOINTMENT MANAGEMENT ---");
            System.out.println("1. Schedule Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. View Appointments by Patient");
            System.out.println("4. View Appointments by Doctor");
            System.out.println("5. Update Appointment Status");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. Demonstrate Appointment Clone");
            System.out.println("0. Back to Main Menu");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: scheduleAppointment(); break;
                case 2: viewAllAppointments(); break;
                case 3: viewAppointmentsByPatient(); break;
                case 4: viewAppointmentsByDoctor(); break;
                case 5: updateAppointmentStatus(); break;
                case 6: cancelAppointment(); break;
                case 7: demonstrateAppointmentClone(); break;
                case 0: back = true; break;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void scheduleAppointment() {
        System.out.println("\n--- SCHEDULE APPOINTMENT ---");

        String patientId = getStringInput("Enter patient ID: ");
        if (patientService.getPatientById(patientId) == null) {
            System.out.println("Patient not found!");
            return;
        }

        String doctorId = getStringInput("Enter doctor ID: ");
        if (doctorService.getDoctorById(doctorId) == null) {
            System.out.println("Doctor not found!");
            return;
        }

        System.out.println("Enter appointment date and time (dd-MM-yyyy HH:mm): ");
        String dateTimeStr = scanner.nextLine();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime appointmentTime = LocalDateTime.parse(dateTimeStr, formatter);

            String id = IdGenerator.newAppointmentId();
            Appointment appointment = new Appointment(id, patientId, doctorId,
                    appointmentTime, AppointmentStatus.SCHEDULED);

            appointmentService.scheduleAppointment(appointment);
            System.out.println("\n✓ Appointment scheduled successfully!");
            System.out.println(appointment);

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format!");
        }
    }

    private static void viewAllAppointments() {
        System.out.println("\n--- ALL APPOINTMENTS ---");
        List<Appointment> appointments = appointmentService.getAllAppointments();

        if (appointments.isEmpty()) {
            System.out.println("No appointments found!");
        } else {
            for (Appointment apt : appointments) {
                System.out.println(apt);
            }
        }
    }

    private static void viewAppointmentsByPatient() {
        String patientId = getStringInput("Enter patient ID: ");
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);

        if (appointments.isEmpty()) {
            System.out.println("No appointments found for this patient!");
        } else {
            for (Appointment apt : appointments) {
                System.out.println(apt);
            }
        }
    }

    private static void viewAppointmentsByDoctor() {
        String doctorId = getStringInput("Enter doctor ID: ");
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);

        if (appointments.isEmpty()) {
            System.out.println("No appointments found for this doctor!");
        } else {
            for (Appointment apt : appointments) {
                System.out.println(apt);
            }
        }
    }

    private static void updateAppointmentStatus() {
        String id = getStringInput("Enter appointment ID: ");

        try {
            Appointment apt = appointmentService.getAppointmentById(id);
            System.out.println("Current status: " + apt.getStatus());

            System.out.println("\nSelect New Status:");
            System.out.println("1. Confirmed");
            System.out.println("2. Completed");
            System.out.println("3. Cancelled");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: appointmentService.confirmAppointment(id); break;
                case 2: appointmentService.completeAppointment(id); break;
                case 3: appointmentService.cancelAppointment(id); break;
                default: System.out.println("Invalid choice!"); return;
            }

            System.out.println("✓ Appointment status updated!");

        } catch (AppointmentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cancelAppointment() {
        String id = getStringInput("Enter appointment ID to cancel: ");

        try {
            appointmentService.cancelAppointment(id);
            System.out.println("✓ Appointment cancelled successfully!");
        } catch (AppointmentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void demonstrateAppointmentClone() {
        System.out.println("\n--- APPOINTMENT CLONE DEMONSTRATION ---");

        Appointment original = new Appointment(
                IdGenerator.newAppointmentId(),
                "PAT2001",
                "DOC1001",
                LocalDateTime.now().plusDays(1),
                AppointmentStatus.SCHEDULED
        );
        original.addNote("symptoms", "Fever and cough");
        original.addNote("priority", "High");

        Appointment cloned = original.clone();

        System.out.println("Original: " + original);
        System.out.println("Cloned: " + cloned);

        cloned.addNote("follow-up", "Required");

        System.out.println("\nAfter adding note to clone:");
        System.out.println("Original notes: " + original.getNotes());
        System.out.println("Cloned notes: " + cloned.getNotes());
        System.out.println("\n✓ Deep copy successful!");
    }

    // ==================== BILL MENU ====================
    private static void billMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- BILLING MANAGEMENT ---");
            System.out.println("1. Generate Bill (using Factory)");
            System.out.println("2. View All Bills");
            System.out.println("3. View Unpaid Bills");
            System.out.println("4. Process Payment");
            System.out.println("5. View Bill Details");
            System.out.println("6. Demonstrate Bill Calculation");
            System.out.println("0. Back to Main Menu");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: generateBill(); break;
                case 2: viewAllBills(); break;
                case 3: viewUnpaidBills(); break;
                case 4: processPayment(); break;
                case 5: viewBillDetails(); break;
                case 6: demonstrateBillCalculation(); break;
                case 0: back = true; break;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void generateBill() {
        System.out.println("\n--- GENERATE BILL (Factory Pattern) ---");
        System.out.println("1. Consultation Bill");
        System.out.println("2. Surgery Bill");
        System.out.println("3. Diagnostic Bill");
        System.out.println("4. Pharmacy Bill");
        System.out.println("5. Emergency Bill");

        int choice = getIntInput("Select bill type: ");
        String appointmentId = getStringInput("Enter appointment ID: ");

        Bill bill = null;

        switch (choice) {
            case 1:
                double consultationFee = getDoubleInput("Enter consultation fee: ");
                bill = BillFactory.createConsultationBill(appointmentId, consultationFee);
                break;
            case 2:
                double surgeryFee = getDoubleInput("Enter surgery fee: ");
                double equipment = getDoubleInput("Enter equipment charges: ");
                bill = BillFactory.createSurgeryBill(appointmentId, surgeryFee, equipment);
                break;
            case 3:
                double testCharges = getDoubleInput("Enter test charges: ");
                bill = BillFactory.createDiagnosticBill(appointmentId, testCharges);
                break;
            case 4:
                double medicineCharges = getDoubleInput("Enter medicine charges: ");
                bill = BillFactory.createPharmacyBill(appointmentId, medicineCharges);
                break;
            case 5:
                double emergencyFee = getDoubleInput("Enter emergency fee: ");
                double additional = getDoubleInput("Enter additional charges: ");
                bill = BillFactory.createEmergencyBill(appointmentId, emergencyFee, additional);
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        if (bill.getBillId() != null) {
            billService.addBill(bill);
            System.out.println("\n✓ Bill generated successfully!");
            System.out.println(bill.generateBill());
        }
    }

    private static void viewAllBills() {
        System.out.println("\n--- ALL BILLS ---");
        List<Bill> bills = billService.getAllBills();

        if (bills.isEmpty()) {
            System.out.println("No bills found!");
        } else {
            for (Bill bill : bills) {
                System.out.println(bill);
            }
        }
    }

    private static void viewUnpaidBills() {
        System.out.println("\n--- UNPAID BILLS ---");
        List<Bill> unpaidBills = billService.getUnpaidBills();

        if (unpaidBills.isEmpty()) {
            System.out.println("No unpaid bills!");
        } else {
            for (Bill bill : unpaidBills) {
                System.out.println(bill);
            }
            System.out.println("\nTotal Pending: ₹" + billService.getTotalPending());
        }
    }

    private static void processPayment() {
        String billId = getStringInput("Enter bill ID to pay: ");
        Bill bill = billService.getBillById(billId);

        if (bill == null) {
            System.out.println("Bill not found!");
            return;
        }

        if (bill.isPaid()) {
            System.out.println("Bill already paid!");
            return;
        }

        System.out.println(bill.generateBill());
        System.out.println("Confirm payment of ₹" + bill.calculateTotal() + "? (yes/no)");

        if (getStringInput("").equalsIgnoreCase("yes")) {
            billService.processBillPayment(billId);
            System.out.println("✓ Payment processed successfully!");
        }
    }

    private static void viewBillDetails() {
        String billId = getStringInput("Enter bill ID: ");
        Bill bill = billService.getBillById(billId);

        if (bill == null) {
            System.out.println("Bill not found!");
        } else {
            System.out.println(bill.generateBill());
        }
    }

    private static void demonstrateBillCalculation() {
        System.out.println("\n--- BILL CALCULATION DEMONSTRATION ---");

        Bill bill = BillFactory.createConsultationBill("APT3001", 1000.0);
        bill.setAdditionalCharges(200.0);

        System.out.println("\nBase Calculation:");
        System.out.println("Consultation: ₹1000.00");
        System.out.println("Additional: ₹200.00");
        System.out.println("Service Charge: ₹" + Constants.SERVICE_CHARGE);
        System.out.println("Total (with 18% tax): ₹" + bill.calculateTotal());

        System.out.println("\nWith custom tax (10%):");
        System.out.println("Total: ₹" + bill.calculateTotal(0.10));

        System.out.println("\nWith 10% discount:");
        System.out.println("Total: ₹" + bill.calculateTotal(10, true));

        System.out.println("\nWith ₹100 flat discount:");
        System.out.println("Total: ₹" + bill.calculateTotal(100, false));
    }

    // ==================== ANALYTICS MENU ====================
    private static void analyticsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- ANALYTICS & REPORTS ---");
            System.out.println("1. Appointment Analytics");
            System.out.println("2. Revenue Report");
            System.out.println("0. Back to Main Menu");

            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1: showAppointmentAnalytics(); break;
                case 2: showRevenueReport(); break;
                case 0: back = true; break;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void showAppointmentAnalytics() {
        System.out.println("\n--- APPOINTMENT ANALYTICS (Streams) ---");

        System.out.println("\nTotal Appointments: " + appointmentService.getAppointmentCount());

        System.out.println("\nAppointments per Doctor:");
        Map<String, Long> perDoctor = appointmentService.getAppointmentsPerDoctor();
        perDoctor.forEach((doctorId, count) ->
                System.out.println("  " + doctorId + ": " + count + " appointments"));
    }

    private static void showRevenueReport() {
        System.out.println("\n--- REVENUE REPORT ---");

        System.out.println("Total Bills: " + billService.getBillCount());
        System.out.println("Paid Bills: " + billService.getPaidBills().size());
        System.out.println("Unpaid Bills: " + billService.getUnpaidBills().size());
        System.out.println("\nTotal Revenue (Paid): ₹" +
                String.format("%.2f", billService.getTotalRevenue()));
        System.out.println("Total Pending: ₹" +
                String.format("%.2f", billService.getTotalPending()));
    }

    // ==================== DATA PERSISTENCE ====================
    private static void saveDataToCSV() {
        System.out.println("\n--- SAVING DATA TO CSV ---");

        try {
            // Create data directory if it doesn't exist
            java.io.File dataDir = new java.io.File(Constants.DATA_DIR);
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            // Save doctors
            List<String[]> doctorRecords = doctorService.getAllDoctors().stream()
                    .map(d -> d.toCSV().split(","))
                    .collect(java.util.stream.Collectors.toList());
            if (!doctorRecords.isEmpty()) {
                CSVUtil.writeCSV(Constants.DOCTORS_FILE, doctorRecords);
                System.out.println("✓ Doctors saved: " + doctorRecords.size());
            }

            // Save patients
            List<String[]> patientRecords = patientService.getAllPatients().stream()
                    .map(p -> p.toCSV().split(","))
                    .collect(java.util.stream.Collectors.toList());
            if (!patientRecords.isEmpty()) {
                CSVUtil.writeCSV(Constants.PATIENTS_FILE, patientRecords);
                System.out.println("✓ Patients saved: " + patientRecords.size());
            }

            // Save appointments
            List<String[]> appointmentRecords = appointmentService.getAllAppointments().stream()
                    .map(a -> a.toCSV().split(","))
                    .collect(java.util.stream.Collectors.toList());
            if (!appointmentRecords.isEmpty()) {
                CSVUtil.writeCSV(Constants.APPOINTMENTS_FILE, appointmentRecords);
                System.out.println("✓ Appointments saved: " + appointmentRecords.size());
            }

            // Save bills
            List<String[]> billRecords = billService.getAllBills().stream()
                    .map(b -> b.toCSV().split(","))
                    .collect(java.util.stream.Collectors.toList());
            if (!billRecords.isEmpty()) {
                CSVUtil.writeCSV(Constants.BILLS_FILE, billRecords);
                System.out.println("✓ Bills saved: " + billRecords.size());
            }

            System.out.println("\n✓ All data saved successfully!");

        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadDataFromCSV() {
        try {
            // Load doctors
            if (new java.io.File(Constants.DOCTORS_FILE).exists()) {
                List<String[]> records = CSVUtil.readCSV(Constants.DOCTORS_FILE);
                for (String[] record : records) {
                    Doctor doctor = Doctor.fromCSV(String.join(",", record));
                    if (doctor != null) doctorService.addDoctor(doctor);
                }
                System.out.println("✓ Loaded " + records.size() + " doctors");
            }

            // Load patients
            if (new java.io.File(Constants.PATIENTS_FILE).exists()) {
                List<String[]> records = CSVUtil.readCSV(Constants.PATIENTS_FILE);
                for (String[] record : records) {
                    Patient patient = Patient.fromCSV(String.join(",", record));
                    if (patient != null) patientService.addPatient(patient);
                }
                System.out.println("✓ Loaded " + records.size() + " patients");
            }

            // Load appointments
            if (new java.io.File(Constants.APPOINTMENTS_FILE).exists()) {
                List<String[]> records = CSVUtil.readCSV(Constants.APPOINTMENTS_FILE);
                for (String[] record : records) {
                    Appointment apt = Appointment.fromCSV(String.join(",", record));
                    if (apt != null) appointmentService.scheduleAppointment(apt);
                }
                System.out.println("✓ Loaded " + records.size() + " appointments");
            }

            // Load bills
            if (new java.io.File(Constants.BILLS_FILE).exists()) {
                List<String[]> records = CSVUtil.readCSV(Constants.BILLS_FILE);
                for (String[] record : records) {
                    Bill bill = Bill.fromCSV(String.join(",", record));
                    if (bill != null) billService.addBill(bill);
                }
                System.out.println("✓ Loaded " + records.size() + " bills");
            }

        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    // ==================== SAMPLE DATA ====================
    private static void loadSampleData() {
        if (!loadData && doctorService.getDoctorCount() == 0) {
            System.out.println("\nLoading sample data...");

            // Add sample doctors
            doctorService.addDoctor(new Doctor(IdGenerator.newDoctorId(),
                    "Dr. Sarah Johnson", 45, "9876543210", Specialization.CARDIOLOGIST, 1500.0));
            doctorService.addDoctor(new Doctor(IdGenerator.newDoctorId(),
                    "Dr. Michael Chen", 38, "9876543211", Specialization.DERMATOLOGIST, 1000.0));
            doctorService.addDoctor(new Doctor(IdGenerator.newDoctorId(),
                    "Dr. Emily Williams", 42, "9876543212", Specialization.PEDIATRICIAN, 1200.0));

            // Add sample patients
            Patient p1 = new Patient(IdGenerator.newPatientId(),
                    "John Doe", 30, "9123456780", "No major health issues");
            p1.addAllergy("Penicillin");
            patientService.addPatient(p1);

            Patient p2 = new Patient(IdGenerator.newPatientId(),
                    "Jane Smith", 25, "9123456781", "Asthma");
            patientService.addPatient(p2);

            System.out.println("✓ Sample data loaded!");
        }
    }

    // ==================== UTILITY METHODS ====================
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}

