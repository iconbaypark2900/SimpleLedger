package org.yup.ml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountBalance {

    private static final String CSV_FILE = "transaction.csv";
    private double balance;

    public AccountBalance(double initialBalance) {
        balance = initialBalance;
    }

    public void loadTransactions() {
        List<Double> payments = new ArrayList<>();
        List<Double> deposits = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double amount = Double.parseDouble(values[1]);
                if (amount < 0) {
                    payments.add(amount);
                } else {
                    deposits.add(amount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Payments:");
        for (Double payment : payments) {
            System.out.println(payment);
        }

        System.out.println("Deposits:");
        for (Double deposit : deposits) {
            System.out.println(deposit);
        }
    }
}

