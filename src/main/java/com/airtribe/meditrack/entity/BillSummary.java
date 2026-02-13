package com.airtribe.meditrack.entity;


// Immutable BillSummary class
public final class BillSummary {
    private final String billId;
    private final String patientName;
    private final String doctorName;
    private final double totalAmount;
    private final boolean isPaid;

    public BillSummary(String billId, String patientName, String doctorName, 
                       double totalAmount, boolean isPaid) {
        this.billId = billId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
    }

    // Only getters (no setters - immutable)
    public String getBillId() { return billId; }
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public double getTotalAmount() { return totalAmount; }
    public boolean isPaid() { return isPaid; }

    @Override
    public String toString() {
        return String.format("BillSummary[ID=%s, Patient=%s, Doctor=%s, Amount=%.2f, Paid=%s]",
                           billId, patientName, doctorName, totalAmount, isPaid);
    }
}
