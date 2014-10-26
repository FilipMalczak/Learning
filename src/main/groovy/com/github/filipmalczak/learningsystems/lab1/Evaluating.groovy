package com.github.filipmalczak.learningsystems.lab1

import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.core.Instances
import weka.core.Utils


class Evaluating {
    static List<Double> evaluate(Classifier classifier, int folds, Instances data){
        Evaluation e = new Evaluation(data)
        e.crossValidateModel(
            classifier,
            data,
            folds,
            new Random(1),
            new Object[0]
        )
        return [
            e.weightedTruePositiveRate(),
            e.weightedFalsePositiveRate(),
            accuracy(e),
            e.weightedPrecision(),
            e.weightedRecall(),
            e.weightedFMeasure()
        ]
    }

    static double accuracy(Evaluation e){
        //salvaged from inside WEKA
        double[] classCounts = new double[e.m_NumClasses];
        double classCountSum = 0;
        for (int i = 0; i < e.m_NumClasses; i++) {
            for (int j = 0; j < e.m_NumClasses; j++) {
                classCounts[i] += e.m_ConfusionMatrix[i][j];
            }
            classCountSum += classCounts[i];
        }
        double acc = 0;
        for (int i = 0; i < e.m_NumClasses; i++) {
            double temp = (e.numTruePositives(i)+e.numTrueNegatives(i))/
                (e.numTruePositives(i)+e.numTrueNegatives(i)+e.numFalsePositives(i)+e.numFalseNegatives(i));
            acc += (temp * classCounts[i]);
        }

        return acc / classCountSum;
    }

    static String evaluation(List<Double> eval) {
        eval.collect { Utils.doubleToString(it, 3).replace(".", ",") }.join(";")
    }
}
