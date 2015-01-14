package com.github.filipmalczak.learningsystems.lab6_ensemble

import com.github.filipmalczak.learningsystems.eval.CrossValidation
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.DataSetResources

DataSet clean = DataSetResources.iris.cleanSet
def classifier = new KnnAdaBoost({5}, 10, 0.7)
println([CrossValidation.evaluate(classifier, clean, 10).csvLine].join(";"))
