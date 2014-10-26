package com.github.filipmalczak.learningsystems.lab1

import weka.classifiers.Classifier
import weka.classifiers.trees.J48


class Classifiers {
    static Classifier buildClassifier(Map args) {
        def out = new J48()
        args.each { k, v ->
            out."$k" = v
        }
        out
    }
}
