package com.github.filipmalczak.learningsystems.lab5_knn

import static com.github.filipmalczak.learningsystems.eval.ExperimentsAnalyzer.*

def path = "/home/phill/repos/groovy/Learning/knn_results.csv"
List<Table> tables = readCSV(new File(path))

List<Table> out = []
tables.each { table ->
    out << findBest(table, ["votingInput", "folds"], "F")
    out << findBest(table, ["k", "folds"], "F")
    out << findBest(table, ["distance", "folds"], "F")
}

new File("./summary.csv").withPrintWriter {
    dumpCSVs(out, it)
}