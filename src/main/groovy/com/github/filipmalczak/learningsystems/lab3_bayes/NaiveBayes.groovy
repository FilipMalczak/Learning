package com.github.filipmalczak.learningsystems.lab3_bayes

import com.github.filipmalczak.learningsystems.model.ClassificationAlgorithm
import com.github.filipmalczak.learningsystems.model.Classifier
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.Instance

import groovy.transform.Canonical

import static java.lang.Math.*

@Canonical
class NaiveBayes implements ClassificationAlgorithm {

    boolean smoothing = true

    static final private double SQRT_2_PI = sqrt(2*PI)

    private Closure<Double> buildNumericalDistribution(String clazz, String attr, DataSet data){
        double sum = 0.0;
        double sumOfSquare = 0.0;
        data.instances.each { Instance i ->
            if (i.classValue == clazz) {
                sum += i[attr]
                sumOfSquare += i[attr]**2
            }
        }
        double mean = sum / data.size
        double stdDev = sqrt((sumOfSquare - ((sum**2)/data.size))/(data.size-1))
        return { val ->
            exp( -1 * ( (val-mean)**2 )/ (2 * (stdDev**2) ) )/(stdDev* SQRT_2_PI)
        }
    }

    private Closure<Double> buildNominalDistribution(String clazz, String attr, DataSet data){
        Map<Object, Double> aprioris = [:].withDefault(smoothing ? {1} : { 0 })
        data.instances.each { Instance i ->
            if (i.classValue == clazz)
                aprioris[i[attr]] ++
        }
        aprioris.keySet().each { String val ->
            aprioris[val] = aprioris[val]/( data.size + (smoothing ? data.scheme.getDomain(attr).size() : 0 ))
        }
        return { val ->
            return aprioris[val]
        }
    }

    @Override
    Classifier buildClassifier(DataSet trainingSet) {
        Model out = new Model()
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
        Map<String, Map<String, Closure<Double>>> distributionPerValue = [:].withDefault { [:] }

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