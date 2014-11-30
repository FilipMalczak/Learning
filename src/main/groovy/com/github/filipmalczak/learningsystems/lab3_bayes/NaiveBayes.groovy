package com.github.filipmalczak.learningsystems.lab3_bayes

import com.github.filipmalczak.learningsystems.model.ClassificationAlgorithm
import com.github.filipmalczak.learningsystems.model.Classifier
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.Instance

import groovy.transform.Canonical

@Canonical
class NaiveBayes implements ClassificationAlgorithm {

    boolean smoothing = true

    private Closure<Double> buildNumericalDistribution(String clazz, String attr, DataSet data){
        if (data.scheme.isNominalAttribute(attr))
            buildNominalDistribution(clazz, attr, data)
        else if (data.scheme.isNumericalAttribute(attr))
            buildNumericalDistribution(clazz, attr, data)
    }

    private Closure<Double> buildNominalDistribution(String clazz, String attr, DataSet data){
        Map<Object, Double> aprioris = [:].withDefault(smoothing ? {1} : { 0 })
        data.instances.each { Instance i ->
            if (i.classValue == clazz)
                aprioris[i[attr]] ++
        }
        aprioris.keySet().each { String val ->
            aprioris[val] = aprioris[val]/( data.size + smoothing ? data.scheme.getDomain(attr).size() : 0 )
        }
        return { val ->
            return aprioris[val]
        }
    }

    @Override
    Classifier buildClassifier(DataSet trainingSet) {
        Model out = new Model([:])
        trainingSet.scheme.classDomain.each { String clazz ->
            trainingSet.scheme.attributeNames.each { String attr ->
                if (attr == trainingSet.scheme.className)
                    out.distributionPerValue[clazz][attr] = out.distributionPerValue[clazz][attr] = { 1.0 }
                else if (trainingSet.scheme.isNominalAttribute(attr))
                    out.distributionPerValue[clazz][attr] = buildNominalDistribution(clazz, attr, trainingSet)
                else if (trainingSet.scheme.isNumericalAttribute(attr))
                    out.distributionPerValue[clazz][attr] =buildNumericalDistribution(clazz, attr, trainingSet)
            }
        }
        out
    }

    @Canonical
    static class Model implements Classifier{

        // [ class -> [ attribute -> { value -> probability } ]]
        Map<String, Map<String, Closure<Double>>> distributionPerValue

        @Override
        String classify(Instance instance) {
            instance.scheme.classDomain.max { String clazz ->
                double prob = 1.0
                instance.scheme.attributeNames.each { String attr ->
                    prob *= distributionPerValue[clazz][attr](instance[attr])
                }
                return prob
            }
        }
    }
}