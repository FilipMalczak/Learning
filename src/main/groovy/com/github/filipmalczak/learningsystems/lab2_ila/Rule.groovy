package com.github.filipmalczak.learningsystems.lab2_ila

import com.github.filipmalczak.learningsystems.model.Instance

import groovy.transform.Canonical

@Canonical
class Rule {

    /**
     * Attribute name -> attribute value
     */
    Map<String, Integer> conditions = [:]
    String classVal

    public boolean matches(Instance instance){
        ! conditions.any { n, v -> instance[n] != v }
    }

    public String toString(){
        def out = "" << "IF "
        def parts = []
        conditions.each { n, v ->
            parts << "$n == $v "
        }
        out << parts.join(" AND ")
        out << "THEN class ==" << classVal
        return out.toString()
    }

}
