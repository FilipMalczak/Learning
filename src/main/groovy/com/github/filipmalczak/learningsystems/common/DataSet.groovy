package com.github.filipmalczak.learningsystems.common

import groovy.transform.Canonical

@Canonical
class DataSet {
    String name
    DataScheme scheme
    List<List> data


    List<Instance> getInstances(){
        data.collect { List vector -> new Instance(vector, scheme) }
    }

    Instance getInstance(int i){
        new Instance(data[i], scheme)
    }

    List getAttributeSnapshot(int attrIdx){
        return data.collect { it[attrIdx] }
    }

    List getAttributeSnapshot(String attrName){
        return getAttributeSnapshot(scheme.getAttributeIndex(attrName))
    }

    /**
     * ~= subset of data with idxs <floor(size*from), ceiling(size*to)> where size is data.size()
     */
    DataSet subset(double from, double to){
        return new DataSet(
            name+"-subset($from; $to)",
            scheme,
            data[Math.floor(from*data.size())..Math.ceil(to*data.size())]
        )
    }

    /**
     * ~= subset of data with idxs <from, to>
     */
    DataSet subset(int from, int to){
        return new DataSet(
            name+"-subset($from; $to)",
            scheme,
            data[from..to]
        )
    }
}
