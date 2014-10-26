package com.github.filipmalczak.learningsystems.lab1

import groovy.transform.Memoized


class Utils {

    static List BOOL_VALS = [true, false]
    static List CONF_VALS = [0.01, 0.05, 0.1, 0.25, 0.4, 0.5]
    static List MIN_OBJ_VALS = [3, 5, 10, 15, 20, 25]
    static List MIN_FOLDS_VALS = [3, 5, 10, 15, 20, 25]
    static FOLDS_VALS = [2, 3, 5, 10]

    @Memoized
    static String booleanVector(List<Boolean> v){
        v.collect { it ? "1" : "0" } .join("")
    }

//    static List<List> cartesianProduct(List<List> lists) {
//        List<List> resultLists = []
//        if (lists.size() == 0) {
//            resultLists << []
//            return resultLists;
//        } else {
//            List firstList = lists.head();
//            List<List> remainingLists = cartesianProduct(lists.subList(1, lists.size()));
//            firstList.each { condition ->
//                remainingLists.each { remainingList ->
//                    ArrayList<T> resultList = new ArrayList<T>();
//                    resultList.add(condition);
//                    resultList.addAll(remainingList);
//                    resultLists.add(resultList);
//                }
//            }
//        }
//        return resultLists;
//    }

    static String numericalVector(List<Number> v){
        ( [weka.core.Utils.doubleToString(v[0], 3).replace(".", ",") ]+ v.tail().collect{ "$it" } ).join(";")
    }
}
