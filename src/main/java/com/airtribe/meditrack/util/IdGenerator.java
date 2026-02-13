package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton utility class for generating unique IDs
 * Demonstrates both eager and lazy initialization
 */
public class IdGenerator {
    // Eager initialization singleton instance
    private static final IdGenerator INSTANCE = new IdGenerator();
    
    private final AtomicInteger doctorCounter;
    private final AtomicInteger patientCounter;
    private final AtomicInteger appointmentCounter;
    private final AtomicInteger billCounter;

    // Private constructor for singleton
    private IdGenerator() {
        this.doctorCounter = new AtomicInteger(1000);
        this.patientCounter = new AtomicInteger(2000);
        this.appointmentCounter = new AtomicInteger(3000);
        this.billCounter = new AtomicInteger(4000);
    }

    /**
     * Get singleton instance (Eager initialization)
     */
    public static IdGenerator getInstance() {
        return INSTANCE;
    }

    // Instance methods for ID generation
    public String generateDoctorId() {
        return "DOC" + doctorCounter.incrementAndGet();
    }

    public String generatePatientId() {
        return "PAT" + patientCounter.incrementAndGet();
    }

    public String generateAppointmentId() {
        return "APT" + appointmentCounter.incrementAndGet();
    }

    public String generateBillId() {
        return "BILL" + billCounter.incrementAndGet();
    }

    // Static methods for convenience (delegates to singleton instance)
    public static String newDoctorId() {
        return getInstance().generateDoctorId();
    }

    public static String newPatientId() {
        return getInstance().generatePatientId();
    }

    public static String newAppointmentId() {
        return getInstance().generateAppointmentId();
    }

    public static String newBillId() {
        return getInstance().generateBillId();
    }

    /**
     * Lazy initialization singleton (alternative approach)
     */
    private static class LazyHolder {
        private static final IdGenerator LAZY_INSTANCE = new IdGenerator();
    }

    public static IdGenerator getLazyInstance() {
        return LazyHolder.LAZY_INSTANCE;
    }

}
