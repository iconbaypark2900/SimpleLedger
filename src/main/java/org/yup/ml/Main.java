package org.yup.ml;

import weka.core.Instances;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Creates scanner, ledger and reports
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

    // Method to add transactions to the ledger
    private static void addMula() {
        // Implement adding a transaction
        LedgerEntry entry = getUserInput(true);
        ledger.addEntry(entry);
        System.out.println("Money added successfully");
    }

    // Method to make a payment and add it to the ledger
    private static void makePayment() {
        LedgerEntry entry = getUserInput(false);
        ledger.addEntry(entry);
        System.out.println("Payment made successfully");
    }

    // Method to get user input for creating a LedgerEntry object
    private static LedgerEntry getUserInput(boolean isDeposit) {
        // Prompt user for transaction details
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

        // Create and return LedgerEntry object
        LedgerEntry entry = new LedgerEntry(date, amount, vendor, description, category);

        return entry;
    }

    // Method to view transaction history
    private static void viewHistory() {
        // Implement viewing transaction history
        ledger.displayEntries(true, true);
    }

    // Method to generate and display reports
    private static void pullupReport() {
        // Implement generating a report
        System.out.println("Report options: ");
        System.out.println("1) View Payments");
        System.out.println("2) View Deposits");
        System.out.println("3) Month-to-date");
        System.out.println("4) Previous month");
        System.out.println("5) Year-to-date");
        System.out.println("6) Previous year");
        System.out.println("7) Search by vendor");
        System.out.println("8) Custom search");

        System.out.println("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        List<LedgerEntry> reportEntries = null;
        String fileName = "transaction.csv";

        // Process user input and call methods
        switch (choice) {
            case 1:
                reports.displayPayments(ledger);
                break;
            case 2:
                reports.displayDeposits(ledger);
                break;
            case 3:
                reports.monthToDate(ledger);
                break;
            case 4:
                reports.previousMonth(ledger);
                break;
            case 5:
                reports.yearToDate(ledger);
                break;
            case 6:
                reports.previousYear(ledger);
            case 7:
                System.out.println("Enter vendor name: ");
                String vendor = scanner.nextLine();
                reports.searchByVendor(ledger, vendor);
                break;
            case 8:
                break;
            default:
                System.out.println("Try again");
                return;
        }

        // Save the generated report if it is not null
        if (reportEntries != null) {
            reports.saveReport(reportEntries, fileName);
        }
    }

    // My grand idea that wasn't so grand
    private static void iffyBot() {


            TransactionClassifier classifier = new TransactionClassifier();
            String classifierPath = "trained_classifier.model";
            System.out.println("Prediction options: ");
            System.out.println("1) Predict balance at the end of the year");
            System.out.println("2) Predict likelihood of overspending in a given category");

            System.out.println("Enter your choice");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Predicting balance at the end of the year...");
                    double currentBalance = ledger.getBalance();
                    System.out.printf("Current balance: %.2f%n", currentBalance);

                    int daysRemaining = daysRemainingInYear();
                    double[] avgDailySpendingAndIncome = ledger.calculateAverageDailySpendingAndIncome();
                    double avgDailySpending = avgDailySpendingAndIncome[0];
                    double avgDailyIncome = avgDailySpendingAndIncome[1];
                    double predictedBalance = currentBalance + (avgDailyIncome - avgDailySpending) * daysRemaining;
                    System.out.printf("Predicted balance at the end of the year: %.2f%n", predictedBalance);
                    break;
                case 2:
                    System.out.println("Enter the category: ");
                    String category = scanner.nextLine();
                    System.out.println("Predicting likelihood of overspending in the given category...");
                    Instances inputData = classifier.prepareInputData(category);
                    double prediction = classifier.predictOverspendingLikelihood(inputData, classifierPath);
                    System.out.printf("The likelihood of overspending in the %s category is %.2f%%%n", category, prediction * 100);
                    break;
                default:
                    System.out.println("No good, try again");
            }
    }

    // Somehow its converting the date
    private static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // Method to calc the number of days remaining in the year. Need it for the iffy bot to work better
    private static int daysRemainingInYear() {
        LocalDate today = LocalDate.now();
        LocalDate endOfYear = today.withDayOfYear(today.lengthOfYear());
        return (int) ChronoUnit.DAYS.between(today, endOfYear);
    }
}


