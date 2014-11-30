package com.github.filipmalczak.learningsystems.lab2_ila

import com.github.filipmalczak.learningsystems.eval.CrossValidation
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.DataSetResources
import com.github.filipmalczak.learningsystems.model.discretization.RangeDiscretizer
import com.github.filipmalczak.learningsystems.model.discretization.UniformDensityDiscretizer

FOLDS = [2, 3, 5, 10]

HEADER = ["ranges", "folds", "TP", "FP", "Accuracy", "Precision", "Recall", "F"].join(";")

def ila = new ILA()
println "RangeDiscretizer"
println "-"*60
[DataSetResources.glass, DataSetResources.iris, DataSetResources.wine].each { DataSet ds ->
    println ds.name
    println HEADER
    (5..20).each { int ranges ->
        FOLDS.each { int fold ->
            DataSet discrete = new RangeDiscretizer(ranges).discretize(ds).cleanSet
            println(["$ranges", "$fold", CrossValidation.evaluate(ila, discrete, fold).csvLine].join(";"))
        }
    }
}
println ""
println ""
println ""
println "UniformDensityDiscretizer"
println "-"*60
[DataSetResources.glass, DataSetResources.iris, DataSetResources.wine].each { DataSet ds ->
    println ds.name
    println HEADER
    (5..20).each { int ranges ->
        FOLDS.each { int fold ->
            DataSet discrete = new UniformDensityDiscretizer(ranges).discretize(ds).cleanSet
            println(["$ranges", "$fold", CrossValidation.evaluate(ila, discrete, fold).csvLine].join(";"))
        }
    }
}