package com.github.filipmalczak.learningsystems.lab3_bayes

import com.github.filipmalczak.learningsystems.eval.CrossValidation
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.DataSetResources
import com.github.filipmalczak.learningsystems.model.discretization.RangeDiscretizer
import com.github.filipmalczak.learningsystems.model.discretization.UniformDensityDiscretizer

FOLDS = [2, 3, 5, 10]

HEADER = ["ranges", "folds", "smooth", "TP", "FP", "Accuracy", "Precision", "Recall", "F"].join(";")

def bayes = new NaiveBayes()
println "<continuous data>"
println "-"*60
[DataSetResources.glass, DataSetResources.iris, DataSetResources.wine].each { DataSet ds ->
    println ds.name
    println HEADER
    [true, false].each { boolean smooth ->
        bayes.smoothing = smooth
        (5..20).each { int ranges ->
            FOLDS.each { int fold ->
                DataSet discrete = new RangeDiscretizer(ranges).discretize(ds).cleanSet
                println(["$ranges", "$fold", "$smooth", CrossValidation.evaluate(bayes, discrete, fold).csvLine].join(";"))
            }
        }
    }
}
println ""
println ""
println ""
println "RangeDiscretizer"
println "-"*60
[DataSetResources.glass, DataSetResources.iris, DataSetResources.wine].each { DataSet ds ->
    println ds.name
    println HEADER
    [true, false].each { boolean smooth ->
        bayes.smoothing = smooth
        (5..20).each { int ranges ->
            FOLDS.each { int fold ->
                DataSet discrete = new RangeDiscretizer(ranges).discretize(ds).cleanSet
                println(["$ranges", "$fold", "$smooth", CrossValidation.evaluate(bayes, discrete, fold).csvLine].join(";"))
            }
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
    [true, false].each { boolean smooth ->
        bayes.smoothing = smooth
        (5..20).each { int ranges ->
            FOLDS.each { int fold ->
                DataSet discrete = new UniformDensityDiscretizer(ranges).discretize(ds).cleanSet
                println(["$ranges", "$fold", "$smooth", CrossValidation.evaluate(bayes, discrete, fold).csvLine].join(";"))
            }
        }
    }
}