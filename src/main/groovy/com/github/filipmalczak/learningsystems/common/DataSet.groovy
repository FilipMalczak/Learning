package com.github.filipmalczak.learningsystems.common

import groovy.transform.Canonical

@Canonical
class DataSet {
    String name
    DataScheme scheme
    List<List> instances


    List getAttributeSnapshot(int attrIdx){
        return instances.collect { it[attrIdx] }
    }

    List getAttributeSnapshot(String attrName){
        return getAttributeSnapshot(scheme.getAttributeIndex(attrName))
    }

    /**
     * ~= subset of instances with idxs <floor(size*from), ceiling(size*to)> where size is instances.size()
     */
    DataSet subset(double from, double to){
        return new DataSet(
            name+"-subset($from; $to)",
            scheme,
            instances[Math.floor(from*instances.size())..Math.ceil(to*instances.size())]
        )
    }

    /**
     * ~= subset of instances with idxs <from, to>
     */
    DataSet subset(int from, int to){
        return new DataSet(
            name+"-subset($from; $to)",
            scheme,
            instances[from..to]
        )
    }
}
