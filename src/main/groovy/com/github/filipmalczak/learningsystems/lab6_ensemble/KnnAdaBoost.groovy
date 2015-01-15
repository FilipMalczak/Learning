package com.github.filipmalczak.learningsystems.lab6_ensemble

import com.github.filipmalczak.learningsystems.lab5_knn.Distance
import com.github.filipmalczak.learningsystems.lab5_knn.Knn
import com.github.filipmalczak.learningsystems.model.ClassificationAlgorithm
import com.github.filipmalczak.learningsystems.model.Classifier
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.Instance
import com.github.filipmalczak.learningsystems.utils.WeightEntry

import groovy.transform.Canonical

/**
 * todo: future work - make it generic; it will need something like interface WeightedClassifier extends Classifier
 */
@Canonical
class KnnAdaBoost implements ClassificationAlgorithm{

    Closure<Integer> k //(int classifierIdx,int numberOfClassifiers) -> int
    int numberOfClassifiers
    double sampleFactor
    Closure<Double> distance = Distance.euclidean()
    boolean withReturning = true

    @Override
    Classifier buildClassifier(DataSet trainingSet) {
        List<WeightEntry<Classifier>> classifierEntries = []
        def initWeight = 1.0/trainingSet.size
        Map<Instance, Double> instanceWeights = [:].withDefault { initWeight }
        numberOfClassifiers.times { int idx ->
            Knn knn = new Knn(
                { Instance neighbour, Instance classified, Collection<Instance> neighbourhood, Closure<Double> distance ->
                    instanceWeights[neighbour]*distance.call(classified.valuesWithoutClass, neighbour.valuesWithoutClass)
                },
                k.call(idx, numberOfClassifiers),
                distance,
                true
            )
            Map<Instance, String> results = [:] // simple memoization issue
            Classifier classifier = knn.buildClassifier(trainingSet.getSample(sampleFactor, withReturning))
            Map<String, Double> classErrors = [:].withDefault {0.0}
            Map<Instance, Double> localInstanceWeights = [:].withDefault { instanceWeights[it] }
            trainingSet.instances.each { Instance instance ->
                def realClass = instance.classValue
                def result = classifier.classify(instance)
                results[instance] = result
                if (result!=realClass)
                    classErrors[result] += localInstanceWeights[instance]
            }
            Map<String, Double> classWeights = [:].withDefault { Double.MAX_VALUE } // if classifier has no error on some class, then lets assume it knows it's sh*t
            classErrors.each { String clazz, double error ->
                classWeights[clazz] = Math.log((1-error)/error)/2.0
            }
            classifierEntries.add(new WeightEntry<Classifier>(classifier, classWeights))
            double newWeigthSum = 0.0
            trainingSet.instances.each { Instance instance ->
                try {
                    def result = results[instance]
                    def multiplier = (result == instance.classValue ? 1.0 : -1.0)
                    localInstanceWeights[instance] = localInstanceWeights[instance] * Math.exp(classWeights[result] * multiplier)
                    newWeigthSum += localInstanceWeights[instance]
                } catch (Throwable t){
                    throw t
                }
            }
            localInstanceWeights.keySet().each { Instance instance ->
                localInstanceWeights[instance] /= newWeigthSum
            }
            instanceWeights = localInstanceWeights
        }
        new EnsembleClassifier(classifierEntries)
    }
}
