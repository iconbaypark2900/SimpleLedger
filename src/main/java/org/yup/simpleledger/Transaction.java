package org.yup.simpleledger;

import java.io.BufferedWriter;
import java.time.LocalDateTime;

public class Transaction {
    private String type;
    private LocalDateTime date;
    private double amount;
    private String vendor;

    public Transaction(String type, LocalDateTime date, double amount, String vendor) {
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.vendor = vendor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
