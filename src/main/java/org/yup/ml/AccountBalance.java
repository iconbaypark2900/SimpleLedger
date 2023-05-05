package org.yup.ml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountBalance {


    private static final String CSV_FILE = "transaction.csv";
    private double balance; // Current balance

    // Co
    public AccountBalance(double initialBalance) {
        balance = initialBalance;
    }

    // Load transactions from the csv file and categorize them as payments or deposits
    public void loadTransactions() {
        List<Double> payments = new ArrayList<>();
        List<Double> deposits = new ArrayList<>();

        // Read the csv file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double amount = Double.parseDouble(values[1]);
                // Categorize the transaction as a payment or deposit based on its amount
                if (amount < 0) {
                    payments.add(amount);
                } else {
                    deposits.add(amount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the payments
        System.out.println("Payments:");
        for (Double payment : payments) {
            System.out.println(payment);
        }

        // Print the deposits
        System.out.println("Deposits:");
        for (Double deposit : deposits) {
            System.out.println(deposit);
        }
    }
}

