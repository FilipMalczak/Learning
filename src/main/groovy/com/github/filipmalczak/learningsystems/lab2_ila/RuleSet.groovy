package com.github.filipmalczak.learningsystems.lab2_ila

import com.github.filipmalczak.learningsystems.model.Classifier
import com.github.filipmalczak.learningsystems.model.Instance

import groovy.transform.Canonical

@Canonical
class RuleSet implements Classifier{

    @Delegate
    List<Rule> rules = []

    @Override
    String classify(Instance instance) {
        return rules.find { Rule rule ->
            rule.matches(instance)
        }?.classVal
    }
}
