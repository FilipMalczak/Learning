package com.github.filipmalczak.learningsystems.lab6_ensemble

import static com.github.filipmalczak.learningsystems.eval.ExperimentsAnalyzer.*

def path = "/home/phill/repos/groovy/Learning/ensemble_results.csv"
List<Table> tables = readCSV(new File(path))

List<Table> toShow = []
List<Table> forDiagrams = []
//numberOfClassfiers;samplingFactor;classificationAlgorithm
tables.each { table ->
    def best = findBest(table, ["numberOfClassfiers", "fold"], "F")
    toShow << best
    forDiagrams << rearrangeForXYChart(best, ["numberOfClassfiers"], "F", "fold")
    best = findBest(table, ["samplingFactor", "fold"], "F")
    toShow << best
    forDiagrams << rearrangeForXYChart(best, ["samplingFactor"], "F", "fold")
    best = findBest(table, ["k", "fold"], "F")
    toShow << best
    forDiagrams << rearrangeForXYChart(best, ["k"], "F", "fold")
    best = findBest(table, ["ensemblingAlgorithm", "fold"], "F")
    toShow << best
    forDiagrams << rearrangeForXYChart(best, ["ensemblingAlgorithm"], "F", "fold")
}

new File("./summary.csv").withPrintWriter {
    dumpCSVs(toShow, it)
}

new File("./forDiagrams.csv").withPrintWriter {
    dumpCSVs(forDiagrams, it)
}