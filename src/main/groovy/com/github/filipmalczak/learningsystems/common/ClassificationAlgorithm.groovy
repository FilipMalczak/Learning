package com.github.filipmalczak.learningsystems.common


interface ClassificationAlgorithm {
    Classifier buildClassifier(DataSet trainingSet)
}
