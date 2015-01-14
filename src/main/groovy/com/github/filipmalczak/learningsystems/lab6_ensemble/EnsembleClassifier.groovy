package com.github.filipmalczak.learningsystems.lab6_ensemble

import com.github.filipmalczak.learningsystems.model.Classifier
import com.github.filipmalczak.learningsystems.model.Instance
import com.github.filipmalczak.learningsystems.utils.WeightEntry

import groovy.transform.Canonical

@Canonical
class EnsembleClassifier implements Classifier {

    List<WeightEntry<Classifier>> entries

    @Override
    String classify(Instance instance) {
        Map<String, Double> results = [:].withDefault { 0.0 }
        entries.each { WeightEntry<Classifier> entry ->
            String clazz = entry.classifier.classify(instance)
            results[clazz] += entry.weights[clazz]
        }
        results.keySet().max { results[it] }
    }
}
