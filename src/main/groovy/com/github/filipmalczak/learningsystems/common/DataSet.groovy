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
}
