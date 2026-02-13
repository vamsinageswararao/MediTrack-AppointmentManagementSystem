package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillType;
import com.airtribe.meditrack.util.DataStore;
import java.util.List;
import java.util.stream.Collectors;

public class BillService {
    private DataStore<Bill> billStore;

    public BillService() {
        this.billStore = new DataStore<>();
    }

    public void addBill(Bill bill) {
        billStore.add(bill);
    }

    public Bill getBillById(String id) {
        return billStore.getById(id);
    }

    public List<Bill> getAllBills() {
        return billStore.getAll();
    }

    public List<Bill> getBillsByAppointment(String appointmentId) {
        return billStore.getAll().stream()
                .filter(bill -> bill.getAppointmentId().equals(appointmentId))
                .collect(Collectors.toList());
    }

    public List<Bill> getUnpaidBills() {
        return billStore.getAll().stream()
                .filter(bill -> !bill.isPaid())
                .collect(Collectors.toList());
    }

    public List<Bill> getPaidBills() {
        return billStore.getAll().stream()
                .filter(Bill::isPaid)
                .collect(Collectors.toList());
    }

    public double getTotalRevenue() {
        return billStore.getAll().stream()
                .filter(Bill::isPaid)
                .mapToDouble(Bill::calculateTotal)
                .sum();
    }

    public double getTotalPending() {
        return billStore.getAll().stream()
                .filter(bill -> !bill.isPaid())
                .mapToDouble(Bill::calculateTotal)
                .sum();
    }

    public void updateBill(Bill bill) {
        billStore.update(bill);
    }

    public void processBillPayment(String billId) {
        Bill bill = billStore.getById(billId);
        if (bill != null) {
            bill.processPayment();
            billStore.update(bill);
        }
    }

    public int getBillCount() {
        return billStore.size();
    }
}
