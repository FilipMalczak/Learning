package com.github.filipmalczak.learningsystems.model


class DataSetResources {
    static final DataSet wine
    static final DataSet iris
    static final DataSet glass
    static final DataSet letters
    static final DataSet column

    static {
        def loader = new ARFFLoader()
        wine = loader.load(0, DataSetResources.classLoader.getResource("wine.arff").newReader())
        iris = loader.load(4, DataSetResources.classLoader.getResource("iris.arff").newReader())
        glass = loader.load(9, DataSetResources.classLoader.getResource("glass.arff").newReader())
        letters = loader.load(0, DataSetResources.classLoader.getResource("letter-recognition.arff").newReader())
        column = loader.load(6, DataSetResources.classLoader.getResource("column_3C_weka.arff").newReader())
    }

}
