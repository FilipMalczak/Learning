package com.github.filipmalczak.learningsystems.common


class CrossValidatorTest extends GroovyTestCase {
    DataSet dataSet
    DataSet glass
    Map<Integer, List<List<Instance>>> expectedFolding // maps folds number to expected result

    void setUp() {
        dataSet = new DataSet(
            "test",
            new DataScheme(0, ["class", "value"], [class: ["A", "B", "C"]]),
            [
                ["A", 1],
                ["A", 11],
                ["A", 21],
                ["A", 31],
                ["A", 41],
                ["A", 51],
                ["B", 2],
                ["B", 12],
                ["B", 22],
                ["C", 3],
                ["C", 13],
                ["C", 23],
                ["C", 33],
                ["C", 43],
                ["C", 53],
                ["C", 63],
                ["C", 73],
                ["C", 83]
            ]
        )
        glass = new ARFFLoader().load(9, this.class.classLoader.getResource("glass.arff").newReader())
        expectedFolding = [
            (2): [
                [
                    ["A", 1],
                    ["A", 21],
                    ["A", 41],
                    ["B", 2],
                    ["B", 22],
                    ["C", 13],
                    ["C", 33],
                    ["C", 53],
                    ["C", 73]
                ],
                [
                    ["A", 11],
                    ["A", 31],
                    ["A", 51],
                    ["B", 12],
                    ["C", 3],
                    ["C", 23],
                    ["C", 43],
                    ["C", 63],
                    ["C", 83]
                ]
            ],
            (3): [
                [
                    ["A", 1],
                    ["A", 31],
                    ["B", 2],
                    ["C", 3],
                    ["C", 33],
                    ["C", 63],
                ],
                [
                    ["A", 11],
                    ["A", 41],
                    ["B", 12],
                    ["C", 13],
                    ["C", 43],
                    ["C", 73]
                ],
                [
                    ["A", 21],
                    ["A", 51],
                    ["B", 22],
                    ["C", 23],
                    ["C", 53],
                    ["C", 83]
                ]
            ]
        ]
    }

    void testGroup3Fold(){
        assert CrossValidator.getFoldedSubsets(dataSet, 3) == expectedFolding[3]
    }

    void testGroup2Fold(){
        assert CrossValidator.getFoldedSubsets(dataSet, 2) == expectedFolding[2]
    }

    void testGlassFolding(){
        assert CrossValidator.getFoldedSubsets(glass, 10).collect {it.size()} == [22, 22, 22, 22, 21, 21, 21, 21, 21, 21]
    }
}
