package com.github.filipmalczak.learningsystems.model


interface ClassificationAlgorithm {
    Classifier buildClassifier(DataSet trainingSet)
}
