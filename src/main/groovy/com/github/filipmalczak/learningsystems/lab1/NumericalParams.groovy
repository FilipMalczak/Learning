package com.github.filipmalczak.learningsystems.lab1

import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.classifiers.trees.J48
import weka.core.Instances
import weka.core.Utils
import weka.core.converters.ArffLoader

import static com.github.filipmalczak.learningsystems.lab1.Utils.cartesianProduct
import static com.github.filipmalczak.learningsystems.lab1.Utils.numericalVector

class NumericalParams {
    static List<String> numericalArgs = [
        "confidenceFactor",
        "minNumObj",
        "numFolds"
    ]

    static List<Double> confs = [0.01, 0.1, 0.2, 0.25, 0.35, 0.5] // above 0.5 we get "WARNING: confidence value for pruning  too high. Error estimate not modified."
    static List<Integer> mins = [2, 4, 5, 6, 10, 20]
    static List<Integer> folds = [3, 4, 5, 8, 10, 20, 25, 50]

    static List<List<Number>> cases = cartesianProduct([ confs, mins, folds ])

    static void main(args) {
        tryData("wine.arff", WekaData.wine)
        tryData("iris.arff", WekaData.iris)
        tryData("glass.arff", WekaData.glass)
    }

    static void tryData(String name, Instances data){

        println name
        println header

        cases.each { List<Number> v ->
            def res = getResults(v, data)
            print  numericalVector(v)+ ";"
            println Evaluating.evaluation(res)
        }
        println "\n\n"
    }

    static String header = (numericalArgs + ["TP", "FP", "Precision", "Recall", "F-measure", ]).join(";")


    static List<Double> getResults(List<Number> vector, Instances data) {
        def algo = new J48()

        /**
         * Uncomment this to get different values than in raport.
         */
//        algo.reducedErrorPruning = true
        numericalArgs.size().times {
            int i
            algo."${numericalArgs[i]}" = vector[i]
        }
        Evaluating.evaluate(algo, data)
    }
}
