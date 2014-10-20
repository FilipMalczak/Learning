package com.github.filipmalczak.learningsystems.lab1

import weka.classifiers.trees.J48
import weka.core.Instances

import static com.github.filipmalczak.learningsystems.lab1.Utils.booleanVector
import static com.github.filipmalczak.learningsystems.lab1.Utils.cartesianProduct


class BinaryParams {
    static List<String> binaryArgs = [
        "binarySplits",
        "reducedErrorPruning",
        "subtreeRaising",
        "unpruned"
    ]

    static List<List<Boolean>> cases = cartesianProduct([ [false, true] ]*binaryArgs.size())

    static void main(args) {

        tryData("wine.arff", WekaData.wine)
        tryData("iris.arff", WekaData.iris)
        tryData("glass.arff", WekaData.glass)

    }

    static void tryData(String name, Instances data){

        println name
        println header

        cases.each { List<Boolean> v ->
            def res = getResults(v, data)
            print booleanVector(v) + ";"
            println Evaluating.evaluation(res)
        }
        println "\n\n"
    }

    static String header = ["Parameter vector", "TP", "FP", "Precision", "Recall", "F-measure", ].join(";")


    static List<Double> getResults(List<Boolean> vector, Instances data) {
        def algo = new J48()
        binaryArgs.size().times {
            int i
            algo."${binaryArgs[i]}" = vector[i]
        }
        Evaluating.evaluate(algo, data)
    }




}