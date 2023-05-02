package org.yup.ml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Ledger {
    private List<LedgerEntry> entries;

    public Ledger() {
        entries = new ArrayList<>();
    }

    public void addEntry(LedgerEntry entry) {
        entries.add(entry);
    }

    public void displayEntries(boolean showDeposits, boolean showPayments) {
        List<LedgerEntry> filteredEntries = entries.stream()
                .filter(entry -> (showDeposits && entry.getAmount() >= 0) || (showPayments && entry.getAmount() < 0))
                .collect(Collectors.toList());

        printEntries(filteredEntries);
    }

    public List<LedgerEntry> filterEntries(Date startDate, Date endDate, String description, String vendor, Double amount) {
        List<LedgerEntry> filteredEntries = entries.stream()
                .filter(entry -> (startDate == null || !entry.getDate().before(startDate)) && (endDate == null || !entry.getDate().after(endDate)))
                .filter(entry -> (description == null || entry.getDescription().equals(description)) && (vendor == null || entry.getVendor().equals(vendor)))
                .filter(entry -> (amount == null || entry.getAmount() == amount))
                .collect(Collectors.toList());

        return filteredEntries;
    }

    private void printEntries(List<LedgerEntry> entries) {
        System.out.println("Date\t\tDescription\tVendor\tAmount");
        System.out.println("---------------------------------------------------");
        for (LedgerEntry entry : entries) {
            System.out.println(String.format("%tF\t%s\t%s\t%.2f", entry.getDate(), entry.getDescription(), entry.getVendor(), entry.getAmount()));
        }
    }
}

