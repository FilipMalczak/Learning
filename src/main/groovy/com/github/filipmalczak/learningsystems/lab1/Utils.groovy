package com.github.filipmalczak.learningsystems.lab1

import groovy.transform.Memoized


class Utils {

    @Memoized
    static String booleanVector(List<Boolean> v){
        v.collect { it ? "1" : "0" } .join("")
    }

    static <T> List<List<T>> cartesianProduct(List<List<T>> lists) {
        List<List<T>> resultLists = new ArrayList<List<T>>();
        if (lists.size() == 0) {
            resultLists.add(new ArrayList<T>());
            return resultLists;
        } else {
            List<T> firstList = lists.get(0);
            List<List<T>> remainingLists = cartesianProduct(lists.subList(1, lists.size()));
            for (T condition : firstList) {
                for (List<T> remainingList : remainingLists) {
                    ArrayList<T> resultList = new ArrayList<T>();
                    resultList.add(condition);
                    resultList.addAll(remainingList);
                    resultLists.add(resultList);
                }
            }
        }
        return resultLists;
    }

    static String numericalVector(List<Number> v){
        ( [weka.core.Utils.doubleToString(v[0], 3).replace(".", ",") ]+ v.tail().collect{ "$it" } ).join(";")
    }
}
