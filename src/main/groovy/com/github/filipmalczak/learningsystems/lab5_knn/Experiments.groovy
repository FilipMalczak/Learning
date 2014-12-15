package com.github.filipmalczak.learningsystems.lab5_knn

import com.github.filipmalczak.learningsystems.eval.CrossValidation
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.DataSetResources

HEADER = ["distance", "voting", "folds", "k", "TP", "FP", "Accuracy", "Precision", "Recall", "F"].join(";")
FOLDS = [2, 3, 5, 10]
KS = [1, 3, 5, 7, 11, 13, 17, 19, 23]
DATA_SETS = [
    DataSetResources.glass,
    DataSetResources.iris,
    DataSetResources.wine,
    DataSetResources.column2,
    DataSetResources.column3
]
DISTANCES = [
    ["Manhattan", {x -> Distance.manhattan()}],
    ["Euclidean", {x -> Distance.euclidean()}],
    ["Minkovsky[3]", {x -> Distance.minkowsky(3)}],
    ["Minkovsky[5]", {x -> Distance.minkowsky(5)}],
    ["Chebyshev", {x -> Distance.chebyshev()}],
    ["Mahalanobis", {x -> Distance.mahalanobis(x)}]
]

DATA_SETS.each { DataSet ds ->
    println ds.name
    println HEADER
    DISTANCES.each {
        String distName = it[0]
        Closure<Closure<Double>> distFactory = it[1];
        [true, false].each { boolean voting ->
            FOLDS.each { int folds ->
                KS.each { int k ->
                    DataSet clean = ds.cleanSet
                    def knn = new KnnBuilder(k, voting, distFactory(clean))
                    println([distName, "$voting", "$folds", "$k", CrossValidation.evaluate(knn, clean, folds).csvLine].join(";"))
                }
            }
        }
    }
    println ""
}


//println "Manhattan"
//println "-"*60
////[DataSetResources.iris].each { DataSet ds ->
//[DataSetResources.glass, DataSetResources.iris, DataSetResources.wine].each { DataSet ds ->
//    println ds.name
//    println HEADER
//    KS.each { int k ->
//        FOLDS.each { int fold ->
//            DataSet clean = ds.cleanSet
//            def knn = new KnnBuilder(k, true, Distance.manhattan())
//            println(["$fold", "$k", CrossValidation.evaluate(knn, clean, fold).csvLine].join(";"))
//        }
//    }
//}
//print "\n"*3
//
//println "Euclidean"
//println "-"*60
//[DataSetResources.glass, DataSetResources.iris, DataSetResources.wine].each { DataSet ds ->
//    println ds.name
//    println HEADER
//    KS.each { int k ->
//        FOLDS.each { int fold ->
//            DataSet clean = ds.cleanSet
//            def knn = new KnnBuilder(k, false, Distance.euclidean())
//            println(["$fold", "$k", CrossValidation.evaluate(knn, clean, fold).csvLine].join(";"))
//        }
//    }
//}
//print "\n"*3
//
//println "Minkovsky[3]"
//println "-"*60
//[DataSetResources.glass, DataSetResources.iris, DataSetResources.wine].each { DataSet ds ->
//    println ds.name
//    println HEADER
//    KS.each { int k ->
//        FOLDS.each { int fold ->
//            DataSet clean = ds.cleanSet
//            def knn = new KnnBuilder(k, false, Distance.minkowsky(3))
//            println(["$fold", "$k", CrossValidation.evaluate(knn, clean, fold).csvLine].join(";"))
//        }
//    }
//}
//print "\n"*3
//
//println "Minkovsky[5]"
//println "-"*60
//[DataSetResources.glass, DataSetResources.iris, DataSetResources.wine].each { DataSet ds ->
//    println ds.name
//    println HEADER
//    KS.each { int k ->
//        FOLDS.each { int fold ->
//            DataSet clean = ds.cleanSet
//            def knn = new KnnBuilder(k, false, Distance.minkowsky(3))
//            println(["$fold", "$k", CrossValidation.evaluate(knn, clean, fold).csvLine].join(";"))
//        }
//    }
//}
//print "\n"*3
//
//println "Chebyshev"
//println "-"*60
//[DataSetResources.glass, DataSetResources.iris, DataSetResources.wine].each { DataSet ds ->
//    println ds.name
//    println HEADER
//    KS.each { int k ->
//        FOLDS.each { int fold ->
//            DataSet clean = ds.cleanSet
//            def knn = new KnnBuilder(k, false, Distance.chebyshev())
//            println(["$fold", "$k", CrossValidation.evaluate(knn, clean, fold).csvLine].join(";"))
//        }
//    }
//}
//print "\n"*3
//
//println "Mahalanobis"
//println "-"*60
//[DataSetResources.glass, DataSetResources.iris, DataSetResources.wine].each { DataSet ds ->
////[DataSetResources.wine].each { DataSet ds ->
//    println ds.name
//    println HEADER
//    KS.each { int k ->
//        FOLDS.each { int fold ->
//            DataSet clean = ds.cleanSet
//            def knn = new KnnBuilder(k, false, Distance.mahalanobis(clean))
//            println(["$fold", "$k", CrossValidation.evaluate(knn, clean, fold).csvLine].join(";"))
//        }
//    }
//}
//print "\n"*3
