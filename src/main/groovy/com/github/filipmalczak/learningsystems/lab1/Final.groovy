package com.github.filipmalczak.learningsystems.lab1


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