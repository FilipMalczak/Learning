package com.github.filipmalczak.learningsystems.common

/**
 * At this point we only allow numeric or nominal attributes. For laboratories sake this should be enough.
 *
 * Methods are quite self-explanatory, fields are explaned with name or with javadoc.
 *
 * If something goes wrong SOME exception is raised.
 */
class DataScheme {
    List<String> attributeNames

    /*
     * Maps nominal attributes into their domains (lists of their values).
     */
    Map<String, List<String>> nominalDomains

    boolean isNominalAttribute(int attrIdx){
        nominalDomains.containsKey(attributeNames[attrIdx])
    }

    boolean isNumericalAttribute(int attrIdx){ return !isNominalAttribute(attrIdx) }

    List getDomain(int attrIdx){
        assert isNominalAttribute(attrIdx)
        return nominalDomains[attributeNames[attrIdx]]
    }

    boolean isNominalAttribute(String attrName){
        assert attributeNames.contains(attrName)
        return nominalDomains.containsKey(attrName)
    }

    boolean isNumericalAttribute(String attrName){ return !isNominalAttribute(attrName) }

    List<String> getDomain(String attrName){
        assert isNominalAttribute(attrName)
        return nominalDomains[attrName]
    }

    int getAttributeIndex(String name){
        def out = attributeNames.indexOf(name)
        assert out>=0
        return out
    }
}