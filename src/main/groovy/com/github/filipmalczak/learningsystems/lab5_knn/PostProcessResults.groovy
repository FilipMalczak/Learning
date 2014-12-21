package com.github.filipmalczak.learningsystems.lab5_knn

import static com.github.filipmalczak.learningsystems.eval.ExperimentsAnalyzer.*

def path = "/home/phill/repos/groovy/Learning/knn_results.csv"
List<Table> tables = readCSV(new File(path))

List<Table> toShow = []
List<Table> forDiagrams = []
tables.each { table ->
    def best = findBest(table, ["votingInput", "folds"], "F")
    toShow << best
    forDiagrams << rearrangeForXYChart(best, ["votingInput"], "F", "folds")
    best = findBest(table, ["k", "folds"], "F")
    toShow << best
    forDiagrams << rearrangeForXYChart(best, ["k"], "F", "folds")
    best = findBest(table, ["distance", "folds"], "F")
    toShow << best
    forDiagrams << rearrangeForXYChart(best, ["distance"], "F", "folds")
}

new File("./summary.csv").withPrintWriter {
    dumpCSVs(toShow, it)
}

new File("./forDiagrams.csv").withPrintWriter {
    dumpCSVs(forDiagrams, it)
}