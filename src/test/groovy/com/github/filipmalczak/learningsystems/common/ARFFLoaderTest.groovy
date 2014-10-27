package com.github.filipmalczak.learningsystems.common


class ARFFLoaderTest extends GroovyTestCase {
    ARFFLoader loader

    void setUp(){
        loader = new ARFFLoader()
    }

    void testLoading(){
        def wine = loader.load(this.class.classLoader.getResource("wine.arff").newReader())
        def iris = loader.load(this.class.classLoader.getResource("iris.arff").newReader())
        def glass = loader.load(this.class.classLoader.getResource("glass.arff").newReader())
        assert ["1",14.23,1.71,2.43,15.6,127,2.8,3.06,0.28,2.29,5.64,1.04,3.92,1065] == wine.instances[0]
        assert ["sepallength", "sepalwidth", "petallength", "petalwidth", "class"] == iris.scheme.attributeNames
        assert [class: ["Iris-setosa","Iris-versicolor","Iris-virginica"]] == iris.scheme.getDomain("class")
    }
}
