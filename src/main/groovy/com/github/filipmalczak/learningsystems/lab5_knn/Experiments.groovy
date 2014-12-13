package com.github.filipmalczak.learningsystems.lab5_knn

import com.github.filipmalczak.learningsystems.eval.CrossValidation
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.DataSetResources

HEADER = ["folds", "k", "TP", "FP", "Accuracy", "Precision", "Recall", "F"].join(";")
FOLDS = [2, 3, 5, 10]
KS = [1, 3, 5, 7, 11, 13, 17, 19, 23]


println "<euclidean>"
println "-"*60
[DataSetResources.glass, DataSetResources.iris, DataSetResources.wine].each { DataSet ds ->
    println ds.name
    println HEADER
    KS.each { int k ->
        def knn = new KnnBuilder(k, Distance.euclidean())
        FOLDS.each { int fold ->
            DataSet clean = ds.cleanSet
            println(["$fold", "$k", CrossValidation.evaluate(knn, clean, fold).csvLine].join(";"))
        }
    }
}

