package com.github.filipmalczak.learningsystems.lab1

import weka.core.Instances

import static weka.core.Utils.doubleToString


class Tables {
    static String header(List<String> fields){
        fields.join(";")+";Folds;TP;FP;Accuracy;Precision;Recall;F-measure"
    }

    static String format(Boolean b){
        b ? "true" : "false"
    }

    static String format(Double d){
        doubleToString(d, 3).replace(".", ",")
    }

    static String format(i){
        "$i"
    }

    static String line(int folds, List config, List<Double> eval){
        config.collect {
            format(it)
        }.join(";") +
            ";$folds;" +
            Evaluating.evaluation(eval)
    }

    static void printTable(Map constants, List<Integer> folds, List<String> changing, Map values, Instances data){
//        println([
//            constants: constants,
//            folds: folds,
//            changing: changing,
//            values: values,
//            data: data.hashCode()
//        ])
        println header(changing)

        def configs = changing.collect {values[it]}
//        println configs
        configs.combinations().each { config ->
            folds.each {
                println line(
                    it,
                    config,
                    Evaluating.evaluate(
                        Classifiers.buildClassifier(constants),
                        it,
                        data
                    )
                )
            }
        }
    }



//    static void tryCombinations(Map constants, List<Integer> folds, List<String> changing, Map values, Instances data) {
//        println([
//            idx: idx,
//            constants: constants,
//            folds: folds,
//            changing: changing,
//            values: values,
//            data: data.hashCode()
//        ])
//        def field = changing[idx]
//        values[field].each { value ->
//            if (idx<changing.size()) {
//                tryCombinations(idx+1, merge(constants, [(field): value]), folds, changing, values, data)
//            } else {
//                def config = changing.collect { constants[it] }
//                folds.each { fold ->
//                    println
//                    )
//                }
//            }
//        }
//    }

    static Map merge(Map m1, Map m2){
        def out = [:]
        m1.each { k, v ->
            out[k] = v
        }
        m2.each { k, v ->
            out[k] = v
        }
        out
    }
}
