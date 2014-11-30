package com.github.filipmalczak.learningsystems.lab2_ila

import com.github.filipmalczak.learningsystems.model.ClassificationAlgorithm
import com.github.filipmalczak.learningsystems.model.Classifier
import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.Instance

class ILA implements ClassificationAlgorithm {

    @Override
    Classifier buildClassifier(DataSet dataSet) {
        List<String> nonClassAttributes = dataSet.scheme.attributeNames.collect()
        nonClassAttributes.remove(dataSet.scheme.className)

        List<DataSet> subtables = createSubtables(dataSet)

        RuleSet out = new RuleSet()

        subtables.size().times {
            DataSet active = subtables.head().fullCopy
            List<DataSet> nonActive = subtables.tail()
            int combinationSize = 1
            while (!active.empty){
                List<List<String>> ruleCandidates = candidates(nonClassAttributes, combinationSize)
                List<Rule> allRules = ruleCandidates.collect { List<String> attrs ->   // all possible rules
                    active.instances.collect { Instance i ->
                        def r = new Rule()
                        r.classVal = i.classValue
                        r.conditions = [:]
                        attrs.each { String a ->
                            r.conditions[a] = i[a]
                        }
                        r
                    }
                }.flatten().toSet().findAll { Rule r ->                  // all rules not covering nonactive instances
                    ! nonActive.any { DataSet na ->
                        na.instances.any { Instance i ->
                            r.matches(i)
                        }
                    }
                }.toList()

                if (allRules.empty){
                    if (combinationSize == nonClassAttributes.size()){
                        def up = new RuntimeException(
                            "ILA is unable to build classifier! There are instances differing only with class attribute!"
                        )
                        throw up
                    }
                    combinationSize++
                    continue
                }

                Rule bestRule = findBestRule(allRules, active)
                List<Instance> remaining = active.instances.findAll { Instance instance ->
                    ! bestRule.matches(instance)
                }
                active.clear()
                active.addAll(remaining.collect { it.values })
                out.add(bestRule)
            }


            subtables.add(subtables.remove(0))
        }
        out
    }

    private List<String> unmask(List<String> attrs, int mask){
        def out = []
        attrs.size().times { int i ->
            if ((1 << i) & mask)
                out << attrs[i]
        }
        return out
    }

    private List<List<String>> candidates(List<String> attrs, int combSize){
        def out = []
        def bitLength = attrs.size()
        (1<<(bitLength-1)).times { int i ->
            def attrSet = unmask(attrs, i)
            if (attrSet.size() == combSize)
                out << attrSet
        }
        return out
    }


    private List<DataSet> createSubtables(DataSet dataSet){
        Map<String, DataSet> subtables = [:].withDefault {dataSet.emptyCopy}
        dataSet.instances.each { Instance instance ->
            subtables[instance.classValue].add(instance.values)
        }
        subtables.values().toList()
    }

    private Rule findBestRule(List<Rule> rules, DataSet table){
        rules.max { Rule r ->
            table.instances.sum { Instance i ->
                r.matches(i) ? 1 : 0
            }
        }
    }
}
