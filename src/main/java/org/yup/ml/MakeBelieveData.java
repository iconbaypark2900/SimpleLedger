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
        String[] vendors = {"Amazon", "Walmart", "Best Buy", "McDonald's", "Target"};
        String[] descriptions = {"Electronics", "Groceries", "Clothing", "Food", "Home"};
        String[] categories = {"Shopping", "Groceries", "Apparel", "Dining", "Home"};

        int numberOfTransactions = 1000;
        String outputPath = "transaction.csv";

        Path path = Paths.get(outputPath);
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath, true))) {
            if (Files.size(path) == 0) {
                bw.write("Date,Description,Vendor,Amount,Category\n");
            }

            for (int i = 0; i < numberOfTransactions; i++) {
                LocalDate date = generateRandomDate();
                int index = generateRandomIndex(vendors.length);
                String vendor = vendors[index];
                String description = descriptions[index];
                double amount = generateRandomAmount();
                String category = categories[index];

                bw.write(date + "," + description + "," + vendor + "," + amount + "," + category + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        MakeBelieveData makeBelieveData = new MakeBelieveData();
        makeBelieveData.trainAndEvaluate();
    }

    private static LocalDate generateRandomDate() {
        Random random = new Random();
        int minYear = 2020;
        int maxYear = 2022;
        int year = random.nextInt(maxYear - minYear + 1) + minYear;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;
        return LocalDate.of(year, month, day);
    }

    private static int generateRandomIndex(int length) {
        Random random = new Random();
        return random.nextInt(length);
    }

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

    public void trainAndEvaluate() {
        TransactionClassifier transactionClassifier = new TransactionClassifier();
        transactionClassifier.trainAndEvaluateClassifier("transaction.csv", "trained_classifier.model");
    }
}
