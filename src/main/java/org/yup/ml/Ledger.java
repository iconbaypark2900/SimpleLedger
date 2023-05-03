package org.yup.ml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Ledger {
    private List<LedgerEntry> entries;
    private static final String csv_file = "transaction.csv";
    private static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");


    public Ledger() {
        entries = new ArrayList<>();
        loadEntriesFromCSV();
    }

    public void addEntry(LedgerEntry entry) {
        entries.add(entry);
        writeEntryToCSV(entry);

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
        System.out.println("Date\t\tDescription\tVendor\tAmount\tCategories");
        System.out.println("---------------------------------------------------");
        for (LedgerEntry entry : entries) {
            System.out.println(String.format("%tF\t%s\t%s\t%.2f\t%s", entry.getDate(), entry.getDescription(), entry.getVendor(), entry.getAmount(), entry.getCategories()));
        }
    }

    private void loadEntriesFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(csv_file))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                Date date = date_format.parse(fields[0]);
                double amount = Double.parseDouble(fields[1]);
                String vendor = fields[2];
                String description = fields[3];
                String categories = fields[4];
                // boolean isDeposit = Boolean.parseBoolean(fields[5]);
                LedgerEntry entry = new LedgerEntry(date, amount, vendor, description,categories);
                entries.add(entry);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

        private void writeEntryToCSV(LedgerEntry entry) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(csv_file, true))) {
                writer.append(date_format.format(entry.getDate())).append(",")
                        .append(Double.toString(entry.getAmount())).append(",")
                        .append(entry.getVendor()).append(",")
                        .append(entry.getDescription()).append(",")
                        .append(entry.getCategories()).append("\n");
                        // .append(Boolean.toString(entry.isDeposit())).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}

