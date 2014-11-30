package com.github.filipmalczak.learningsystems.model.discretization

import com.github.filipmalczak.learningsystems.model.DataSet
import com.github.filipmalczak.learningsystems.model.Instance


abstract class AbstractDiscretizer implements Discretizer {

    @Override
    public DataSet discretize(DataSet dataSet) {
        DataSet out = dataSet.emptyCopy
        Map<String, List<Number>> cutsForAttrName = [:]
        Map<String, List<String>> domains = [:]
        List<String> toDiscretize = dataSet.scheme.attributeNames.findAll { String name -> dataSet.scheme.isNumericalAttribute(name) }
        toDiscretize.each { String attrName ->
            int idx = dataSet.scheme.attributeNames.indexOf(attrName)
            cutsForAttrName[attrName] = getCuts(idx, dataSet)
            domains[attrName] = getDiscreteDomain(cutsForAttrName[attrName])
        }
        dataSet.instances.each { Instance instance ->
            Instance newInstance = instance
            toDiscretize.each { String attrName ->
                int idx = dataSet.scheme.attributeNames.indexOf(attrName)
                newInstance = discretizeInstance(cutsForAttrName[attrName], newInstance, idx, domains[attrName])
            }
            out.add(newInstance.values)
        }
        out
    }

    private static List<String> getDiscreteDomain(List<Number> cuts){
        List<String> out = ["[-inf; ${cuts[0]}]"]
        (cuts.size()-2).times { int idx ->
            out.add "[${cuts[idx]}; ${cuts[idx+1]}]"
        }
        out.add "[${cuts.last()}; inf]"
        out
    }

    private Instance discretizeInstance(List<Number> cuts, Instance instance, int attrIdx, List<String> newDomain){
        Number value = instance[attrIdx]
        int targetRange = 0
        cuts.eachWithIndex{ double cut, int i ->
            if (value > cut)
                targetRange = j+1
        }
        instance.modified(attrIdx, newDomain[targetRange])
    }
}
