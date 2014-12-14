package com.github.filipmalczak.learningsystems.lab5_knn

import com.github.filipmalczak.learningsystems.model.ClassificationAlgorithm
import com.github.filipmalczak.learningsystems.model.Classifier
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.Instance

import static Math.exp

import groovy.transform.Canonical

@Canonical
class KnnBuilder implements ClassificationAlgorithm{
    int k;
    boolean voting
    Closure<Double> distance;

    @Override
    Classifier buildClassifier(DataSet trainingSet) {
        assert trainingSet.scheme.attributeNames.every { trainingSet.scheme.className==it || trainingSet.scheme.isNumericalAttribute(it) }
        voting ? new KnnModelWithVoting(trainingSet) : new KnnModelNoVoting(trainingSet);
    }

    static List<Number> removeClass(Instance instance){
        def out = [*(instance.values)]
        out.remove(instance.scheme.classIdx)
        return out
    }

    @Canonical
    class KnnModelWithVoting implements Classifier {

        DataSet model;

        @Override
        String classify(Instance instance) {
            def dist = distance.memoizeAtMost(2*k)
            def preRemovedClass = removeClass(instance)
            def nearestNeighbours = model.instances.sort(
                false,
                {
                    dist(
                        preRemovedClass,
                        removeClass(it)
                    )
                }
            )[(-k)..-1];
            double distanceExpSum = nearestNeighbours.sum { exp(dist(preRemovedClass, removeClass(it))) }
            Map<String, Double> classValues = [:].withDefault {0.0}
            nearestNeighbours.each {
                classValues[it.classValue]+= exp(dist(preRemovedClass, removeClass(it)))/distanceExpSum
            }
            classValues.max { it.value }.key
        }
    }

    @Canonical
    class KnnModelNoVoting implements Classifier {

        DataSet model;

        @Override
        String classify(Instance instance) {
            def dist = distance.memoizeAtMost(2*k)
            def preRemovedClass = removeClass(instance)
            def nearestNeighbours = model.instances.sort(
                false,
                {
                    dist(
                        preRemovedClass,
                        removeClass(it)
                    )
                }
            )[(-k)..-1];
            //todo: voting! http://www.statsoft.pl/textbook/stathome_stat.html?http://www.statsoft.pl/textbook/stknn.html
            def grouped = nearestNeighbours.groupBy { it.classValue }
            return grouped.keySet().max { k1, k2 ->
                grouped[k1].size() < grouped[k2].size() ?
                    -1 :
                    (
                        grouped[k1].size() > grouped[k2].size() ?
                            1 :
                            dist(preRemovedClass, removeClass(grouped[k1].min {
                                dist(preRemovedClass, removeClass(it))
                            })) > dist(preRemovedClass, removeClass(grouped[k2].min {
                                dist(preRemovedClass, removeClass(it))
                            })) ?
                                -1 : 1
                    )
            }
        }
    }
}
