package com.github.filipmalczak.learningsystems.eval

import static java.lang.Math.abs

class Evaluation {

    Map<String, TestOutcome> resultsForClass = [:]

    public Evaluation(List<String> classDomain){
        classDomain.each {
            resultsForClass[it] = new TestOutcome()
        }
    }

    public void addResult(String result, String expected){
        if (result == expected){
            for (String i: resultsForClass.keySet()) {
                TestOutcome entry = resultsForClass[i]
                if (i == expected)
                    entry.tp++
                else
                    entry.tn++
            }
        } else {
            for (String i: resultsForClass.keySet()) {
                TestOutcome entry = resultsForClass[i]
                if (i == expected)
                    entry.fn++
                else if (i == result)
                    entry.fp++
                else
                    entry.tn++
            }
        }
    }

    public int getCountOfClass(String className){
        resultsForClass[className].tp + resultsForClass[className].fn
    }

    public int getInstancesCount(){
        int out=0
        resultsForClass[(resultsForClass.keySet() as List).head()].with {
            out += tp + tn + fp + fn
        }
        out
    }

    public float getTPRate(String className){
        (resultsForClass[className].tp / getCountOfClass(className)) as float
    }

    public float getRecall(String className){
        getTPRate(className)
    }

    public float getFPRate(String className){
        (resultsForClass[className].fp / (resultsForClass[className].fp + resultsForClass[className].tn)) as float
    }

    public float getPrecision(String className){
        (resultsForClass[className].tp / (resultsForClass[className].tp + resultsForClass[className].fp)) as float
    }

    public float getFMeasure(String className){
        def prec = getPrecision(className)
        def rec = getRecall(className)
        if (abs(prec)<0.000001) // yay floats!
            return 0.0
        ((2 * prec * rec ) / (prec + rec)) as float
    }

    public float getAccuracy(String className){
        ((resultsForClass[className].tp + resultsForClass[className].tn) / getInstancesCount()) as float
    }

    public float getWeightedTPRate(){
        float temp = 0
        resultsForClass.keySet().each {
            temp += getTPRate(it)*getCountOfClass(it)
        }
        temp / getInstancesCount()
    }

    public float getWeightedRecall(){
        getWeightedTPRate()
    }

    public float getWeightedFPRate(){
        float temp = 0
        resultsForClass.keySet().each {
            temp += getFPRate(it)*getCountOfClass(it)
        }
        temp / getInstancesCount()
    }

    public float getWeightedPrecision(){
        float temp = 0
        resultsForClass.keySet().each {
            temp += getPrecision(it)*getCountOfClass(it)
        }
        temp / getInstancesCount()
    }

    public float getWeightedFMeasure(){
        float temp = 0
        resultsForClass.keySet().each {
            temp += getFMeasure(it)*getCountOfClass(it)
        }
        temp / getInstancesCount()
    }

    public float getWeightedAccuracy(){
        float temp = 0
        resultsForClass.keySet().each {
            temp += getAccuracy(it)*getCountOfClass(it)
        }
        temp / getInstancesCount()
    }

    @Override
    public String toString(){
        "{" +
            "'TP': $weightedTPRate, 'FP': $weightedFPRate, " +
            "'Acc': $weightedAccuracy, 'Prec': $weightedPrecision, 'Rec': $weightedPrecision, " +
            "'F': $weightedFMeasure" +
            "}"
    }

}
