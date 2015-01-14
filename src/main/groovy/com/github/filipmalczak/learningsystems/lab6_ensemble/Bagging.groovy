package com.github.filipmalczak.learningsystems.lab6_ensemble

import com.github.filipmalczak.learningsystems.model.ClassificationAlgorithm
import com.github.filipmalczak.learningsystems.model.Classifier
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.utils.WeightEntry

import groovy.transform.Canonical

@Canonical
class Bagging implements ClassificationAlgorithm{
    ClassificationAlgorithm classificationAlgorithm
    int numberOfClassifiers
    double sampleFactor
    boolean withReturning = true

    @Override
    Classifier buildClassifier(DataSet trainingSet) {
        List<WeightEntry<Classifier>> entries = []
        numberOfClassifiers.times {
            Classifier classifier = classificationAlgorithm.buildClassifier(trainingSet.getSample(sampleFactor, withReturning))
            Map<String, Double> weights = [:].withDefault { 0.0 }
            trainingSet.instances.each {
                def clazz = classifier.classify(it)
                if (clazz==it.classValue)
                    weights[clazz] += 1.0
            }
            entries.add new WeightEntry<Classifier>(classifier, weights)
        }
        new EnsembleClassifier(entries)
    }
}
