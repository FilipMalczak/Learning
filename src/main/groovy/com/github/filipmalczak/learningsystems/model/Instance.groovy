package com.github.filipmalczak.learningsystems.model

import groovy.transform.Immutable
import groovy.transform.Memoized

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

    Instance modified(String attrName, value){
        modified(scheme.attributeNames.indexOf(attrName), value)
    }

    Instance modified(int attrIdx, value){
        new Instance(values[0..<attrIdx] + [ value ] + values[(attrIdx+1)..<values.size()], scheme)
    }

    @Memoized
    List getValuesWithoutClass(){
        values[0..(classIdx-1)]+ ((classIdx+1<values.size()-2) ? values[(classIdx+1)..(values.size()-2)] : [] )
    }
}
