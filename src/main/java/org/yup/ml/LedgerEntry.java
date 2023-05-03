package org.yup.ml;

import java.util.Date;

public class LedgerEntry {
    private Date date;
    private String description;
    private String vendor;
    private double amount;

    private String categories;
    private boolean isDeposit;

    public LedgerEntry(Date date, double amount, String vendor,String description, String categories) {
        this.date = date;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.categories = categories;
        this.isDeposit = isDeposit();
    }

    public boolean isDeposit() {
        return isDeposit;
    }

    private void setDeposit(boolean deposit) {
        isDeposit = deposit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategories() {
        return categories;
    }
}
