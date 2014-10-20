package com.github.filipmalczak.learningsystems.lab1

import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.classifiers.trees.J48
import weka.core.Instances
import weka.core.Utils

import groovy.transform.Memoized


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
            print vector(v) + ";"
            println evaluation(res)
        }
        println "\n\n"
    }

    static String header = ["Parameter vector", "TP", "FP", "Precision", "Recall", "F-measure", ].join(";")

    @Memoized
    static String vector(List<Boolean> v){
        v.collect { it ? "1" : "0" } .join("")
    }

    static <T> List<List<T>> cartesianProduct(List<List<T>> lists) {
        List<List<T>> resultLists = new ArrayList<List<T>>();
        if (lists.size() == 0) {
            resultLists.add(new ArrayList<T>());
            return resultLists;
        } else {
            List<T> firstList = lists.get(0);
            List<List<T>> remainingLists = cartesianProduct(lists.subList(1, lists.size()));
            for (T condition : firstList) {
                for (List<T> remainingList : remainingLists) {
                    ArrayList<T> resultList = new ArrayList<T>();
                    resultList.add(condition);
                    resultList.addAll(remainingList);
                    resultLists.add(resultList);
                }
            }
        }
        return resultLists;
    }

    static List<Double> getResults(List<Boolean> vector, Instances data) {
        def algo = new J48()
        binaryArgs.size().times {
            int i
            algo."${binaryArgs[i]}" = vector[i]
        }
        evaluate(algo, data)
    }

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