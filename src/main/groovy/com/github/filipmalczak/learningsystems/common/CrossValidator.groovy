package com.github.filipmalczak.learningsystems.common

class CrossValidator {
    static void moveSomeInstances(List<Instance> from, List<Instance> to, int howMany){
        try {
            howMany.times {
                to << from.head()
                from.remove(0)
            }
        } catch (NoSuchElementException ignored) {}
    }

    static List<List<Instance>> getFoldedSubsets(DataSet dataSet, int folds){
        def groupedByClass = dataSet.scheme.classDomain.collect dataSet.&getInstancesForClass
        def out = (0..<folds).collect { [] }
        int outIdx = 0
        while (!groupedByClass.empty) {
            def currentClassGroup = groupedByClass.head()
            groupedByClass = groupedByClass.tail()
            while (!currentClassGroup.empty) {
                out[outIdx].add(currentClassGroup.head())
                currentClassGroup = currentClassGroup.tail()
                outIdx = ((outIdx+1) % folds)
            }
        }
//        def classSizes = groupedByClass.collect { it.size()  }
//        def perGroup = classSizes.collect { it/folds as int }
//
//        out.each { List<Instance> foldedGroup ->
//            groupedByClass.eachWithIndex { List<Instance> classGroup, int classGroupIdx ->
//                moveSomeInstances(classGroup, foldedGroup, perGroup[classGroupIdx])
//            }
//        }
//        groupedByClass.each {
//            out.last().addAll(it)
//        }
        return out
    }
}
