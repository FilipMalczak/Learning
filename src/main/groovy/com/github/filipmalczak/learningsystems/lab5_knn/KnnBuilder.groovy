package com.github.filipmalczak.learningsystems.lab5_knn

import com.github.filipmalczak.learningsystems.model.ClassificationAlgorithm
import com.github.filipmalczak.learningsystems.model.Classifier
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.Instance

import groovy.transform.Canonical

@Canonical
class KnnBuilder implements ClassificationAlgorithm{
    int k;
    Closure<Double> distance;

    @Override
    Classifier buildClassifier(DataSet trainingSet) {
        assert trainingSet.scheme.attributeNames.every { trainingSet.scheme.className==it || trainingSet.scheme.isNumericalAttribute(it) }
        new KnnModel(trainingSet);
    }

    @Canonical
    class KnnModel implements Classifier {

        DataSet model;

        static List<Number> removeClass(Instance instance){
            def out = [*(instance.values)]
            out.remove(instance.scheme.classIdx)
            return out
        }

        @Override
        String classify(Instance instance) {
            def preRemovedClass = removeClass(instance)
            def nearestNeighbours = model.instances.sort(
                false,
                {
                    distance(
                        preRemovedClass,
                        removeClass(it)
                    )
                }
            )[-(k+1)..-1];
            //todo: voting! http://www.statsoft.pl/textbook/stathome_stat.html?http://www.statsoft.pl/textbook/stknn.html
            def grouped = nearestNeighbours.groupBy { it.classValue }
            return grouped.max { e -> e.value.size() }.key
        }
    }
}
