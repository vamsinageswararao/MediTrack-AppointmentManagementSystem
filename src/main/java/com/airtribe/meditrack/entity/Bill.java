package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interfaces.Payable;
import com.airtribe.meditrack.constants.Constants;

public class Bill extends MedicalEntity implements Payable {
    private String appointmentId;
    private BillType billType;
    private double consultationFee;
    private double additionalCharges;
    private boolean isPaid;

    public Bill(String billId, String appointmentId, BillType billType,
                double consultationFee, double additionalCharges) {
        super(billId);
        this.appointmentId = appointmentId;
        this.billType = billType;
        this.consultationFee = consultationFee;
        this.additionalCharges = additionalCharges;
        this.isPaid = false;
    }

    // Getters
    public String getBillId() { return id; }
    public String getAppointmentId() { return appointmentId; }
    public BillType getBillType() { return billType; }
    public double getConsultationFee() { return consultationFee; }
    public double getAdditionalCharges() { return additionalCharges; }
    public boolean isPaid() { return isPaid; }

    // Setters
    public void setAdditionalCharges(double additionalCharges) { 
        this.additionalCharges = additionalCharges;
        markAsUpdated();
    }
    
    public void setPaid(boolean paid) { 
        isPaid = paid;
        markAsUpdated();
    }

    @Override
    public String getEntityType() {
        return "Bill";
    }

    @Override
    public boolean isValid() {
        return id != null && !id.isEmpty() &&
               appointmentId != null && !appointmentId.isEmpty() &&
               consultationFee >= 0;
    }

    // Calculate subtotal (before tax)
    public double calculateSubtotal() {
        return consultationFee + additionalCharges + Constants.SERVICE_CHARGE;
    }

    // Calculate tax amount
    public double calculateTax() {
        return calculateSubtotal() * Constants.TAX_RATE;
    }

    @Override
    public double calculateTotal() {
        return calculateSubtotal() + calculateTax();
    }

    // Method overloading: calculate total with custom tax rate
    public double calculateTotal(double customTaxRate) {
        return calculateSubtotal() * (1 + customTaxRate);
    }

    // Method overloading: calculate total with discount
    public double calculateTotal(double discount, boolean isPercentage) {
        double subtotal = calculateSubtotal();
        double discountAmount = isPercentage ? (subtotal * discount / 100) : discount;
        return (subtotal - discountAmount) * (1 + Constants.TAX_RATE);
    }

    @Override
    public void processPayment() {
        this.isPaid = true;
        markAsUpdated();
    }

    public String generateBill() {
        StringBuilder bill = new StringBuilder();
        bill.append("\n========================================\n");
        bill.append("           MEDITRACK BILL              \n");
        bill.append("========================================\n");
        bill.append(String.format("Bill ID: %s\n", id));
        bill.append(String.format("Appointment ID: %s\n", appointmentId));
        bill.append(String.format("Bill Type: %s\n", billType));
        bill.append("----------------------------------------\n");
        bill.append(String.format("Consultation Fee: ₹%.2f\n", consultationFee));
        bill.append(String.format("Additional Charges: ₹%.2f\n", additionalCharges));
        bill.append(String.format("Service Charge: ₹%.2f\n", Constants.SERVICE_CHARGE));
        bill.append("----------------------------------------\n");
        bill.append(String.format("Subtotal: ₹%.2f\n", calculateSubtotal()));
        bill.append(String.format("Tax (%.0f%%): ₹%.2f\n", Constants.TAX_RATE * 100, calculateTax()));
        bill.append("========================================\n");
        bill.append(String.format("TOTAL: ₹%.2f\n", calculateTotal()));
        bill.append("========================================\n");
        bill.append(String.format("Status: %s\n", isPaid ? "PAID" : "UNPAID"));
        bill.append("========================================\n");
        return bill.toString();
    }

    @Override
    public String toString() {
        return String.format("Bill[ID=%s, Type=%s, Appointment=%s, Total=₹%.2f, Paid=%s]",
                           id, billType, appointmentId, calculateTotal(), isPaid);
    }

    // CSV representation
    public String toCSV() {
        return String.join(",", id, appointmentId, billType.name(),
                          String.valueOf(consultationFee), String.valueOf(additionalCharges),
                          String.valueOf(isPaid));
    }

    public static Bill fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 6) {
            Bill bill = new Bill(
                parts[0], parts[1], BillType.valueOf(parts[2]),
                Double.parseDouble(parts[3]), Double.parseDouble(parts[4])
            );
            bill.setPaid(Boolean.parseBoolean(parts[5]));
            return bill;
        }
        return null;
    }
}
