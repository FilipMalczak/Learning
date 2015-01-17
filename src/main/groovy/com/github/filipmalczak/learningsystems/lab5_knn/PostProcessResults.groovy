package com.github.filipmalczak.learningsystems.lab5_knn

import static com.github.filipmalczak.learningsystems.eval.ExperimentsAnalyzer.*

def path = "/home/phill/repos/groovy/Learning/knn_results.csv"
List<Table> tables = readCSV(new File(path))

List<Table> toShow = []
List<Table> forDiagrams = []
tables.each { table ->
    def best = findBest(table, ["votingInput", "fold"], "F")
    toShow << best
    forDiagrams << rearrangeForXYChart(best, ["votingInput"], "F", "fold")
    best = findBest(table, ["k", "fold"], "F")
    toShow << best
    forDiagrams << rearrangeForXYChart(best, ["k"], "F", "fold")
    best = findBest(table, ["distance", "fold"], "F")
    toShow << best
    forDiagrams << rearrangeForXYChart(best, ["distance"], "F", "fold")
}

new File("./summary.csv").withPrintWriter {
    dumpCSVs(toShow, it)
}

new File("./forDiagrams.csv").withPrintWriter {
    dumpCSVs(forDiagrams, it)
}