package org.yup.ml;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Reports {
    public void monthToDate(Ledger ledger) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfMonth = currentDate.withDayOfMonth(1);
        generateReport(ledger, startOfMonth, currentDate);
    }

    public void previousMonth(Ledger ledger) {
        LocalDate currentDate = LocalDate.now();
        YearMonth lastMonth = YearMonth.from(currentDate).minusMonths(1);
        LocalDate startOfMonth = lastMonth.atDay(1);
        LocalDate endOfMonth = lastMonth.atEndOfMonth();
        generateReport(ledger, startOfMonth, endOfMonth);
    }

    public void yearToDate(Ledger ledger) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfYear = currentDate.withDayOfYear(1);
        generateReport(ledger, startOfYear, currentDate);
    }

    public void previousYear(Ledger ledger) {
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfLastYear = currentDate.minusYears(1).withDayOfYear(1);
        LocalDate endOfLastYear = currentDate.minusYears(1).withDayOfYear(currentDate.lengthOfYear());
        generateReport(ledger, startOfLastYear, endOfLastYear);
    }

    public void searchByVendor(Ledger ledger, String vendor) {
        List<LedgerEntry> filteredEntries = ledger.filterEntries(null, null, null, vendor, null);
        displayReport(filteredEntries);
    }

    public void customSearch(Ledger ledger, LocalDate startDate, LocalDate endDate, String description, String vendor, Double amount) {
        List<LedgerEntry> filteredEntries = ledger.filterEntries(convertToDate(startDate), convertToDate(endDate), description, vendor, amount);
        displayReport(filteredEntries);
    }

    private void generateReport(Ledger ledger, LocalDate startDate, LocalDate endDate) {
        List<LedgerEntry> filteredEntries = ledger.filterEntries(convertToDate(startDate), convertToDate(endDate), null, null, null);
        displayReport(filteredEntries);
    }

    private void displayReport(List<LedgerEntry> entries) {
        System.out.println("Date\t\tDescription\tVendor\tAmount");
        System.out.println("---------------------------------------------------");
        for (LedgerEntry entry : entries) {
            System.out.println(String.format("%tF\t%s\t%s\t%.2f", entry.getDate(), entry.getDescription(), entry.getVendor(), entry.getAmount()));
        }
    }

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

