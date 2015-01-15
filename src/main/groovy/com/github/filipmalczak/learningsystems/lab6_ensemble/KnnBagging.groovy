package com.github.filipmalczak.learningsystems.lab6_ensemble

import com.github.filipmalczak.learningsystems.lab5_knn.Distance
import com.github.filipmalczak.learningsystems.lab5_knn.Knn
import com.github.filipmalczak.learningsystems.lab5_knn.ResultInput
import com.github.filipmalczak.learningsystems.model.ClassificationAlgorithm
import com.github.filipmalczak.learningsystems.model.Classifier
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.utils.WeightEntry

import groovy.transform.Canonical

@Canonical
class KnnBagging implements ClassificationAlgorithm{
    int numberOfClassifiers
    Closure<Integer> k //(int classifierIdx,int numberOfClassifiers) -> int
    double sampleFactor
    boolean withReturning = true

    @Override
    Classifier buildClassifier(DataSet trainingSet) {
        List<WeightEntry<Classifier>> entries = []
        numberOfClassifiers.times {
            Knn knn = new Knn(ResultInput.weightedWithDistance, k.call(it, numberOfClassifiers), Distance.euclidean(), true)
            Classifier classifier = knn.buildClassifier(trainingSet.getSample(sampleFactor, withReturning))
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
