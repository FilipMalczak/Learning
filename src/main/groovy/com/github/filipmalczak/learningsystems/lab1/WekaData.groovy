package com.github.filipmalczak.learningsystems.lab1

import weka.core.Instances
import weka.core.converters.ArffLoader


class WekaData {
    private static @Lazy ArffLoader loader = new ArffLoader()

    static @Lazy Instances wine = loadArffResource("wine.arff", 0)
    static @Lazy Instances iris = loadArffResource("iris.arff", 4)
    static @Lazy Instances glass = loadArffResource("glass.arff", 9)

    private static Instances loadArffResource(String name, int classIdx) {
        loader.setSource(BinaryParams.classLoader.getResource(name).newInputStream())
        def out = loader.dataSet
        loader.reset()
        out.classIndex = classIdx
        return out
    }
}
