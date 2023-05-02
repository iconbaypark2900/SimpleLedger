package org.yup.ml;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Ledger ledger = new Ledger();

    private static final Reports reports = new Reports();

    public static void main(String[] args) {
        boolean exitApp = false;

        while (!exitApp) {
            System.out.println("Personal Finance Assistant");
            System.out.println("1) Add Money to account");
            System.out.println("2) Make a Payment");
            System.out.println("3) View transaction history");
            System.out.println("4) Generate report");
            System.out.println("5) Make predictions");
            System.out.println("0) Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMula();
                    break;
                case 2:
                    makePayment();
                    break;
                case 3:
                    viewHistory();
                    break;
                case 4:
                    pullupReport();
                    break;
                case 5:
                    iffyBot();
                case 0:
                    exitApp = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addMula() {
        // Implement adding a transaction
        LedgerEntry entry = getUserInput(true);
        ledger.addEntry(entry);
        System.out.println("Money added successfully");
    }

    private static void makePayment() {
        LedgerEntry entry = getUserInput(false);
        ledger.addEntry(entry);
        System.out.println("Payment made successfully");
    }

    private static LedgerEntry getUserInput(boolean isDeposit) {
        System.out.println("Enter Date");
        String dateString = scanner.nextLine();
        LocalDate localDate = LocalDate.parse(dateString);
        Date date = convertToDate(localDate);

        System.out.println("Enter amount");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter vendor");
        String vendor = scanner.nextLine();

        System.out.println("Enter category");
        String category = scanner.nextLine();

        System.out.println("Enter description");
        String description = scanner.nextLine();

        LedgerEntry entry = new LedgerEntry(date, amount, vendor, description, isDeposit);

        return entry;
    }

    private static void viewHistory() {
        // Implement viewing transaction history
        ledger.displayEntries(true, true);
    }

    private static void pullupReport() {
        // Implement generating a report
        System.out.println("Report options: ");
        System.out.println("1) Month-to-date");
        System.out.println("2) Previous month");
        System.out.println("3) Year-to-date");
        System.out.println("4) Previous year");
        System.out.println("5) Search by vendor");
        System.out.println("6) Custom search");

        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                reports.monthToDate(ledger);
                break;
            case 2:
                reports.previousMonth(ledger);
                break;
            case 3:
                reports.yearToDate(ledger);
                break;
            case 4:
                reports.previousYear(ledger);
            case 5:
                System.out.println("Enter vendor name: ");
                String vendor = scanner.nextLine();
                reports.searchByVendor(ledger, vendor);
                break;
            case 6:
                break;
            default:
                System.out.println("Try again");
        }
    }

    private static void iffyBot() {
        // Implement making predictions using machine learning models
        System.out.println("Prediction options: ");
        System.out.println("1) Predict balance at the end of the month");
        System.out.println("2) Predict likelihood of overspending in a given category");

        System.out.println("Enter your choice");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                break;
            case 2:
                System.out.println("Enter the category: ");
                String category = scanner.nextLine();
                break;
            default:
                System.out.println("No good, try again");
        }
    }

    private static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}


