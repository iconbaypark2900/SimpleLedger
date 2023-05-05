package org.yup.ml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reports {

    // Display payments from the ledger
    public void displayPayments(Ledger ledger) {
        List<LedgerEntry> entries = ledger.getEntries();
        List<LedgerEntry> payments = new ArrayList<>();
        for(LedgerEntry entry : entries) {
            if (entry.getAmount() < 0) {
                payments.add(entry);
            }
        }
        displayReport(payments);
    }

    // Display deposits from the ledger
    public void displayDeposits(Ledger ledger) {
        List<LedgerEntry> entries = ledger.getEntries();
        List<LedgerEntry> deposits = new ArrayList<>();
        for (LedgerEntry entry : entries) {
            if (entry.getAmount() > 0) {
                deposits.add(entry);
            }
        }
        displayReport(deposits);
    }

    // Generate a report for the current month to date
    public void monthToDate(Ledger ledger) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfMonth = currentDate.withDayOfMonth(1);
        generateReport(ledger, startOfMonth, currentDate);
    }

    // Generate a report for the previous month
    public void previousMonth(Ledger ledger) {
        LocalDate currentDate = LocalDate.now();
        YearMonth lastMonth = YearMonth.from(currentDate).minusMonths(1);
        LocalDate startOfMonth = lastMonth.atDay(1);
        LocalDate endOfMonth = lastMonth.atEndOfMonth();
        generateReport(ledger, startOfMonth, endOfMonth);
    }

    // Generate a report for the current year to date
    public void yearToDate(Ledger ledger) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfYear = currentDate.withDayOfYear(1);
        generateReport(ledger, startOfYear, currentDate);
    }

    // Generate a report for the previous year
    public void previousYear(Ledger ledger) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfLastYear = currentDate.minusYears(1).withDayOfYear(1);
        LocalDate endOfLastYear = currentDate.minusYears(1).withDayOfYear(currentDate.lengthOfYear());
        generateReport(ledger, startOfLastYear, endOfLastYear);
    }

    // Generate a report with entries filtered by a specific vendor
    public void searchByVendor(Ledger ledger, String vendor) {
        List<LedgerEntry> filteredEntries = ledger.filterEntries(null, null, null, vendor, null);
        displayReport(filteredEntries);
    }

    // Yeah somewhere over the rainbow
    public void customSearch(Ledger ledger, LocalDate startDate, LocalDate endDate, String description, String vendor, Double amount) {
        List<LedgerEntry> filteredEntries = ledger.filterEntries(convertToDate(startDate), convertToDate(endDate), description, vendor, amount);
        displayReport(filteredEntries);
    }

    // Generate a report with entries filterd by the specified date range ex:
    private void generateReport(Ledger ledger, LocalDate startDate, LocalDate endDate) {
        List<LedgerEntry> filteredEntries = ledger.filterEntries(convertToDate(startDate), convertToDate(endDate), null, null, null);
        displayReport(filteredEntries);
    }

    // Display the report
    private void displayReport(List<LedgerEntry> entries) {
        System.out.println("Date\t\tDescription\tVendor\tAmount");
        System.out.println("---------------------------------------------------");
        for (LedgerEntry entry : entries) {
            System.out.println(String.format("%tF\t%s\t%s\t%.2f", entry.getDate(), entry.getDescription(), entry.getVendor(), entry.getAmount()));
        }
    }

    // Save report file
    public void saveReport(List<LedgerEntry> entries, String fileName) {
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Date,Description,Vendor,Amount");
            for (LedgerEntry entry : entries) {
                writer.printf("%tF,%s,%s,%.2f%n", entry.getDate(), entry.getDescription(), entry.getVendor(), entry.getAmount());
            }
            System.out.println("Report saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving report to file: " + e.getMessage());
        }
    }

    // Convert a LocalDate obj
    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

