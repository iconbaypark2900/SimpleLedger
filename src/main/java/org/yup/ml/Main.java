package org.yup.ml;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
    }

    private static void makePayment() {

    }

    private static void viewHistory() {
        // Implement viewing transaction history
    }

    private static void pullupReport() {
        // Implement generating a report
    }

    private static void iffyBot() {
        // Implement making predictions using machine learning models
    }
}


