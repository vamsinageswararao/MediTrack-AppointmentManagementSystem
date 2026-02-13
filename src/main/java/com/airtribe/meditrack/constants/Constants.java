package com.airtribe.meditrack.constants;

public class Constants {
    // Date formats
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm";
    
    // File paths

    public static final String DATA_DIR = "data";
    public static final String DOCTORS_FILE = "data/doctors.csv";
    public static final String PATIENTS_FILE = "data/patients.csv";
    public static final String APPOINTMENTS_FILE = "data/appointments.csv";
    public static final String BILLS_FILE = "data/bills.csv";
    
    // Tax rates
    public static final double TAX_RATE = 0.18; // 18% GST
    public static final double SERVICE_CHARGE = 50.0;
    
    // Application info
    public static final String APP_NAME = "MediTrack";
    public static final String APP_VERSION = "1.0.0";

    static {
        System.out.println("Loading MediTrack Configuration...");
        System.out.println("Tax Rate: " + (TAX_RATE * 100) + "%");
        System.out.println("Service Charge: ₹" + SERVICE_CHARGE);
    }
    
    private Constants() {
    }
}
