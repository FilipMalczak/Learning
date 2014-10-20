package com.github.filipmalczak.learningsystems.lab1

import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.core.Instances
import weka.core.Utils


class Evaluating {
    static List<Double> evaluate(Classifier classifier, Instances data){
        Evaluation e = new Evaluation(data)
        e.crossValidateModel(
            classifier,
            data,
            10,
            new Random(1),
            new Object[0]
        )
        return [
            e.weightedTruePositiveRate(),
            e.weightedFalsePositiveRate(),
            e.weightedPrecision(),
            e.weightedRecall(),
            e.weightedFMeasure()
        ]
    }

    static String evaluation(List<Double> eval) {
        eval.collect { Utils.doubleToString(it, 3).replace(".", ",") }.join(";")
    }
}
