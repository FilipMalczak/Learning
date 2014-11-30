package com.github.filipmalczak.learningsystems.eval

import com.github.filipmalczak.learningsystems.model.ClassificationAlgorithm
import com.github.filipmalczak.learningsystems.model.Classifier
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.Instance

public class CrossValidation{

    public static Evaluation evaluate(ClassificationAlgorithm algorithm, DataSet dataSet, int numFolds = 5){
        List<DataSet> folds = getFolds(dataSet, numFolds)

        Evaluation out = null

        numFolds.times {
            Classifier classifier = algorithm.buildClassifier(folds.tail().sum() as DataSet)
            out = doEvaluate(folds.head(), classifier, out)
            folds.add(folds.remove(0))
        }

        out
    }

    private static List<DataSet> getFolds(DataSet dataSet, int numFolds){
        List<DataSet> folds = []
        numFolds.times {
            folds.add(dataSet.emptyCopy)
        }

        dataSet.eachWithIndex { List instance, Integer i ->
            folds[i%numFolds].add(instance)
        }

        folds
    }

    private static Evaluation doEvaluate(DataSet dataSet, Classifier classifier, Evaluation partial=null){

        Evaluation out = partial ?: new Evaluation(dataSet.scheme.classDomain)
        dataSet.instances.each { Instance instance ->
            out.addResult(classifier.classify(instance), instance.classValue)
        }
        out
    }
}
