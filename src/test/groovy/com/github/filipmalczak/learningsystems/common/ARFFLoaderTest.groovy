package com.github.filipmalczak.learningsystems.common


class ARFFLoaderTest extends GroovyTestCase {
    ARFFLoader loader

    void setUp(){
        loader = new ARFFLoader()
    }

    void testLoading(){
        def wine = loader.load(0, this.class.classLoader.getResource("wine.arff").newReader())
        def iris = loader.load(4, this.class.classLoader.getResource("iris.arff").newReader())
        def glass = loader.load(9, this.class.classLoader.getResource("glass.arff").newReader())
        assert ["1",14.23,1.71,2.43,15.6,127,2.8,3.06,0.28,2.29,5.64,1.04,3.92,1065.0] == wine.data[0]
        assert ["sepallength", "sepalwidth", "petallength", "petalwidth", "class"] == iris.scheme.attributeNames
        assert ["Iris-setosa","Iris-versicolor","Iris-virginica"] == iris.scheme.getDomain("class")

        assert wine.data.size() == 178
        assert iris.data.size() == 150
        assert glass.data.size() == 214

        assert glass.getInstance(0)["'Fe'"] == 0.0
        assert glass.getInstance(3)["'Na'"] == 14.4
    }
}
