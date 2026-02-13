package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillType;
import com.airtribe.meditrack.constants.Constants;

/**
 * Factory class for creating different types of bills
 * Demonstrates Factory Design Pattern
 */
public class BillFactory {
    
    /**
     * Create a consultation bill
     */
    public static Bill createConsultationBill(String appointmentId, double consultationFee) {
        String billId = IdGenerator.newBillId();
        return new Bill(billId, appointmentId, BillType.CONSULTATION, consultationFee, 0.0);
    }

    /**
     * Create a surgery bill
     */
    public static Bill createSurgeryBill(String appointmentId, double surgeryFee, double equipmentCharges) {
        String billId = IdGenerator.newBillId();
        return new Bill(billId, appointmentId, BillType.SURGERY, surgeryFee, equipmentCharges);
    }

    /**
     * Create a diagnostic bill
     */
    public static Bill createDiagnosticBill(String appointmentId, double testCharges) {
        String billId = IdGenerator.newBillId();
        return new Bill(billId, appointmentId, BillType.DIAGNOSTIC, 0.0, testCharges);
    }

    /**
     * Create a pharmacy bill
     */
    public static Bill createPharmacyBill(String appointmentId, double medicineCharges) {
        String billId = IdGenerator.newBillId();
        return new Bill(billId, appointmentId, BillType.PHARMACY, 0.0, medicineCharges);
    }

    /**
     * Create an emergency bill
     */
    public static Bill createEmergencyBill(String appointmentId, double emergencyFee, double additionalCharges) {
        String billId = IdGenerator.newBillId();
        Bill bill = new Bill(billId, appointmentId, BillType.EMERGENCY, emergencyFee, additionalCharges);
        return bill;
    }

    /**
     * Create a bill based on BillType enum
     */
    public static Bill createBill(BillType billType, String appointmentId, double baseFee, double additionalCharges) {
        String billId = IdGenerator.newBillId();
        return new Bill(billId, appointmentId, billType, baseFee, additionalCharges);
    }

    /**
     * Create a bill from appointment and doctor consultation fee
     */
    public static Bill createBillFromAppointment(String appointmentId, double doctorFee) {
        return createConsultationBill(appointmentId, doctorFee);
    }

    private BillFactory() {
        // Prevent instantiation
    }
}
