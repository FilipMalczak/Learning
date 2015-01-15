package com.github.filipmalczak.learningsystems.lab6_ensemble

import com.github.filipmalczak.learningsystems.eval.CrossValidation
import com.github.filipmalczak.learningsystems.eval.Evaluation
import com.github.filipmalczak.learningsystems.lab5_knn.Distance
import com.github.filipmalczak.learningsystems.lab5_knn.Knn
import com.github.filipmalczak.learningsystems.lab5_knn.ResultInput
import com.github.filipmalczak.learningsystems.model.ClassificationAlgorithm
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.DataSetResources

List<DataSet> dataSets = [
    DataSetResources.wine,
    DataSetResources.iris,
    DataSetResources.glass
]

List<Integer> numbersOfClassifiers = [
    2,
    3,
    5,
    10,
    25
]

List<Double> samplingFactors = [
    0.1,
    0.5,
    0.75,
    0.9,
    1.0
]

Map<Closure<Integer>> ks = [
    "const": {idx, size -> 3},
    "growing": {idx, size -> 3 + Math.round(4.0*idx/size) as Integer},
    "falling": {idx, size -> 7 - Math.round(3.0*idx/size) as Integer}
]

List<Integer> folds = [
    2,
    3,
    5,
    10
]

println "dataSet;fold;numberOfClassfiers;samplingFactor;classificationAlgorithm;${Evaluation.csvHeader}"
dataSets.each { DataSet ds ->
    numbersOfClassifiers.each { int noc ->
        samplingFactors.each { double sf ->
            ks.each { String kName, Closure<Integer> k ->
                [
                    new KnnBagging(noc, k, sf),
                    new KnnAdaBoost(k, noc, sf) // todo: awfully ugly; change order of args
                ].each { ClassificationAlgorithm ca ->
                    folds.each { int fold ->
                        println "${ds.name};$fold;$noc;$sf;${ca.class.simpleName};${CrossValidation.evaluate(ca, ds.cleanSet, fold).csvLine}"
                    }

                }
            }
        }
    }
}