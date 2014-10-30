package com.github.filipmalczak.learningsystems.common

import groovy.transform.Immutable

@Immutable
class Instance extends AbstractList{
    final @Delegate List values
    final @Delegate DataScheme scheme

    def getAt(String attrName){
        values[scheme.getAttributeIndex(attrName)]
    }

    String getClassValue(){
        getAt(classIdx)
    }
}
