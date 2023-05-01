package org.yup.ml;


import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;

public class TransactionClassifier {

    public void trainAndEvaluateClassifier(String datasetPath, String classifierPath) {
        try {
            // Load the dataset from a CSV file
            Instances data = loadDataset(datasetPath);

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

    private Instances loadDataset(String datasetPath) throws IOException {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(datasetPath));
        Instances data = loader.getDataSet();
        return data;
    }

    private Instances[] splitDataset(Instances data, int trainPercentage) {
        int trainSize = (int) Math.round(data.numInstances() * trainPercentage / 100);
        int testSize = data.numInstances() - trainSize;
        Instances trainData = new Instances(data, 0, trainSize);
        Instances testData = new Instances(data, trainSize, testSize);
        return new Instances[]{trainData, testData};
    }

    private NaiveBayes trainClassifier(Instances trainData) throws Exception {
        NaiveBayes classifier = new NaiveBayes();
        classifier.buildClassifier(trainData);
        return classifier;
    }

    private void evaluateClassifier(NaiveBayes classifier, Instances testData) throws Exception {
        Evaluation eval = new Evaluation(testData);
        eval.evaluateModel(classifier, testData);
        System.out.println(eval.toSummaryString());
    }

    private void saveClassifier(NaiveBayes classifier, String classifierPath) throws Exception {
        SerializationHelper.write(classifierPath, classifier);
    }
}

