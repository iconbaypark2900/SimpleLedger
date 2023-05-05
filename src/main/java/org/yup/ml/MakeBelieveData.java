package org.yup.ml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Random;

public class MakeBelieveData {

    public static void main(String[] args) {
        // Yeah found it on the internet and it started to work
        System.setProperty("weka.packageManager.offline", "true");
        System.setProperty("weka.core.welcome_disabled", "true");

        // Examples to give the iffy bot some criteria
        String[] vendors = {"Amazon", "Walmart", "Best Buy", "ULine", "Target"};
        String[] descriptions = {"Electronics", "Groceries", "Clothing", "Office Supplies", "Home"};
        String[] categories = {"Shopping", "Groceries", "Apparel", "Work", "Home"};
        // create field [5] in order to show whether a transaction is a deposit or withdrawal

        // Create A Balance for iffy bot
        AccountBalance accountBalance = new AccountBalance(1_000_000);
        accountBalance.loadTransactions();

        // How many transactions I want; Creates a 1000 everytime class is ran
        int numberOfTransactions = 1000;
        String outputPath = "transaction.csv";

        // Create output file just in case
        Path path = Paths.get(outputPath);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write to csv
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath, true))) {
            if (Files.size(path) == 0) {
                bw.write("Date, Amount, Vendor, Description, Category\n");
            }

            for (int i = 0; i < numberOfTransactions; i++) {
                LocalDate date = generateRandomDate();
                int index = generateRandomIndex(vendors.length);
                String vendor = vendors[index];
                String description = descriptions[index];
                double amount = generateRandomAmount();
                String category = categories[index];

                String line = date + "," + amount + "," + vendor + "," + description + "," + category + "\n";
                System.out.println(line);
                bw.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Magic
        MakeBelieveData makeBelieveData = new MakeBelieveData();
        makeBelieveData.trainAndEvaluate();
    }

    // Generates random date
    private static LocalDate generateRandomDate() {
        Random random = new Random();
        int minYear = 2020;
        int maxYear = 2023;
        int year = random.nextInt(maxYear - minYear + 1) + minYear;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;
        return LocalDate.of(year, month, day);
    }

    // Generate a random index
    private static int generateRandomIndex(int length) {
        Random random = new Random();
        return random.nextInt(length);
    }

    // Generate a random amount between the specified range
    private static double generateRandomAmount() {
        Random random = new Random();
        double minAmount = -1000;
        double maxAmount = 1000;
        double amount = minAmount + (maxAmount - minAmount) * random.nextDouble();

        // Round the amount value to two decimal places
        BigDecimal roundedAmount = new BigDecimal(Double.toString(amount));
        roundedAmount = roundedAmount.setScale(2, RoundingMode.HALF_UP);

        return roundedAmount.doubleValue();
    }

    // Magic pt.2
    public void trainAndEvaluate() {
        TransactionClassifier transactionClassifier = new TransactionClassifier();
        transactionClassifier.trainAndEvaluateClassifier("transaction.csv", "trained_classifier1.model");
    }
}
