package com.github.filipmalczak.learningsystems.lab1

println "=================================================================="
println "STANDARD PRUNING"
println "=================================================================="

//standard pruning
println ""
println "WINE"
println ""


Tables.printTable(
    [reducedErrorPruning: false, unpruned: false],
    Utils.FOLDS_VALS,
    ["binarySplits", "subtreeRaising", "minNumObj", "confidenceFactor"],
    [
        minNumObj: Utils.MIN_OBJ_VALS,
        confidenceFactor: Utils.CONF_VALS,
        binarySplits: Utils.BOOL_VALS,
        subtreeRaising: Utils.BOOL_VALS
    ],
    WekaData.wine
)

println ""
println "IRIS"
println ""

Tables.printTable(
    [reducedErrorPruning: false, unpruned: false],
    Utils.FOLDS_VALS,
    ["binarySplits", "subtreeRaising", "minNumObj", "confidenceFactor"],
    [
        minNumObj: Utils.MIN_OBJ_VALS,
        confidenceFactor: Utils.CONF_VALS,
        binarySplits: Utils.BOOL_VALS,
        subtreeRaising: Utils.BOOL_VALS
    ],
    WekaData.iris
)

println ""
println "GLASS"
println ""

Tables.printTable(
    [reducedErrorPruning: false, unpruned: false],
    Utils.FOLDS_VALS,
    ["binarySplits", "subtreeRaising", "minNumObj", "confidenceFactor"],
    [
        minNumObj: Utils.MIN_OBJ_VALS,
        confidenceFactor: Utils.CONF_VALS,
        binarySplits: Utils.BOOL_VALS,
        subtreeRaising: Utils.BOOL_VALS
    ],
    WekaData.glass
)

println "=================================================================="
println "STANDARD PRUNING"
println "=================================================================="

//reduced error pruning


println ""
println "WINE"
println ""

Tables.printTable(
    [reducedErrorPruning: true, unpruned: false],
    Utils.FOLDS_VALS,
    ["binarySplits", "subtreeRaising", "minNumObj", "numFolds"],
    [
        minNumObj: Utils.MIN_OBJ_VALS,
        numFolds: Utils.MIN_FOLDS_VALS,
        binarySplits: Utils.BOOL_VALS,
        subtreeRaising: Utils.BOOL_VALS
    ],
    WekaData.wine
)

println ""
println "IRIS"
println ""

Tables.printTable(
    [reducedErrorPruning: true, unpruned: false],
    Utils.FOLDS_VALS,
    ["binarySplits", "subtreeRaising", "minNumObj", "numFolds"],
    [
        minNumObj: Utils.MIN_OBJ_VALS,
        numFolds: Utils.MIN_FOLDS_VALS,
        binarySplits: Utils.BOOL_VALS,
        subtreeRaising: Utils.BOOL_VALS
    ],
    WekaData.iris
)

println ""
println "GLASS"
println ""

Tables.printTable(
    [reducedErrorPruning: true, unpruned: false],
    Utils.FOLDS_VALS,
    ["binarySplits", "subtreeRaising", "minNumObj", "numFolds"],
    [
        minNumObj: Utils.MIN_OBJ_VALS,
        numFolds: Utils.MIN_FOLDS_VALS,
        binarySplits: Utils.BOOL_VALS,
        subtreeRaising: Utils.BOOL_VALS
    ],
    WekaData.glass
)

println "=================================================================="
println "STANDARD PRUNING"
println "=================================================================="

// no pruning

println ""
println "WINE"
println ""

Tables.printTable(
    [reducedErrorPruning: false, unpruned: true],
    Utils.FOLDS_VALS,
    ["binarySplits", "subtreeRaising", "minNumObj"],
    [
        minNumObj: Utils.MIN_OBJ_VALS,
        binarySplits: Utils.BOOL_VALS,
        subtreeRaising: Utils.BOOL_VALS
    ],
    WekaData.wine
)

println ""
println "IRIS"
println ""

Tables.printTable(
    [reducedErrorPruning: false, unpruned: true],
    Utils.FOLDS_VALS,
    ["binarySplits", "subtreeRaising", "minNumObj"],
    [
        minNumObj: Utils.MIN_OBJ_VALS,
        binarySplits: Utils.BOOL_VALS,
        subtreeRaising: Utils.BOOL_VALS
    ],
    WekaData.iris
)

println ""
println "GLASS"
println ""

Tables.printTable(
    [reducedErrorPruning: false, unpruned: true],
    Utils.FOLDS_VALS,
    ["binarySplits", "subtreeRaising", "minNumObj"],
    [
        minNumObj: Utils.MIN_OBJ_VALS,
        binarySplits: Utils.BOOL_VALS,
        subtreeRaising: Utils.BOOL_VALS
    ],
    WekaData.glass
)