package org.yup.ml;


import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionClassifier {

    // Train and evaluate a classifier using the dataset provided in the given path
    public void trainAndEvaluateClassifier(String datasetPath, String classifierPath) {
        try {
            // Load the dataset from a CSV file
            Instances data = loadDataset(datasetPath);
            data.setClassIndex(data.numAttributes() - 1);
            // Split the dataset into training (80%) and testing (20%) sets
            Instances[] datasets = splitDataset(data, 80);
            Instances trainData = datasets[0];
            Instances testData = datasets[1];

            // Train the naive Bayes classifier
            NaiveBayes classifier = trainClassifier(trainData);

            // Evaluate the classifier's performance
            evaluateClassifier(classifier, testData);

            // Save the trained classifier for use in the application
            saveClassifier(classifier, classifierPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load the dataset from the csv file
    private Instances loadDataset(String datasetPath) throws IOException {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(datasetPath));
        Instances data = loader.getDataSet();
        return data;
    }

    // Split the dataset into training and testing sets based on the given %
    private Instances[] splitDataset(Instances data, int trainPercentage) {
        int trainSize = (int) Math.round(data.numInstances() * trainPercentage / 100);
        int testSize = data.numInstances() - trainSize;
        Instances trainData = new Instances(data, 0, trainSize);
        Instances testData = new Instances(data, trainSize, testSize);
        return new Instances[]{trainData, testData};
    }

    // Train a naive Bayes classifier using the provided training data
    private NaiveBayes trainClassifier(Instances trainData) throws Exception {
        NaiveBayes classifier = new NaiveBayes();
        classifier.buildClassifier(trainData);
        return classifier;
    }

    // Evaluate the classifier's performance using the provided test data
    private void evaluateClassifier(NaiveBayes classifier, Instances testData) throws Exception {
        Evaluation eval = new Evaluation(testData);
        eval.evaluateModel(classifier, testData);
        System.out.println(eval.toSummaryString());
    }

    // Save the trained classifier to a file
    private void saveClassifier(NaiveBayes classifier, String classifierPath) throws Exception {
        SerializationHelper.write(classifierPath, classifier);
    }

    // Prepare the input data for prediction
    public Instances prepareInputData(String category) {
        // Create a new dataset with the same structure as your training data
        Instances inputData = new Instances("inputData", createAttributes(), 1);
        inputData.setClassIndex(inputData.numAttributes() - 1);

        // Create a new instance with the input data
        Instance instance = new DenseInstance(inputData.numAttributes());
        instance.setValue(inputData.attribute("category"), category);
        // Set other attributes if needed (e.g., date, amount, etc.)

        // Add the instance to the dataset
        inputData.add(instance);

        return inputData;
    }

    // Predict the overspending likelihood using the trained classifier and input data
    public double predictOverspendingLikelihood(Instances inputData, String classifierPath) {
        try {
            // Load the trained classifier
            NaiveBayes classifier = (NaiveBayes) SerializationHelper.read(classifierPath);

            // Predict the class value (overspending likelihood) for the input data
            double prediction = classifier.classifyInstance(inputData.firstInstance());

            return prediction;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Create the attributes for the dataset
    private ArrayList<Attribute> createAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();

        // Add attributes according to your dataset structure
        attributes.add(new Attribute("category", (List<String>) null));
        // Add other attributes if needed (e.g., date, amount, etc.)

        // Add the class attribute (overspending likelihood)
        ArrayList<String> classValues = new ArrayList<>(Arrays.asList("0", "1"));
        attributes.add(new Attribute("overspending", classValues));

        return attributes;
    }
}

