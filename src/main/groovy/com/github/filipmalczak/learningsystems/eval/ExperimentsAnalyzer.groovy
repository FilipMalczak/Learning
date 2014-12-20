package com.github.filipmalczak.learningsystems.eval

import groovy.transform.Canonical

import java.text.DecimalFormat


class ExperimentsAnalyzer {
    @Canonical
    static class Table {
        String name
        List<String> header
        List<List<Number>> values = []

        int getIdx(String columnName){
            header.indexOf(columnName)
        }

        int getWidth(){
            header.size()
        }
    }

    static List<String> parts(String line){
        line.replaceAll(",", ".").split(";").findAll()
    }

    static enum State {
        READY, READING_HEADER, READING_DATA
    }

    static List<Table> readCSV(File f){

        State state = State.READY
        List<Table> out = []
        Table currentTable;
        f.eachLine {
            List<String> p = parts(it)
            if (p.size()==1) {// table name
                assert state == State.READY
                currentTable = new Table(p[0])
                state = State.READING_HEADER
            } else if (p.size()==0){
                assert state == State.READING_DATA
                out.add currentTable
                currentTable = null
                state = State.READY
            } else {
                if (state == State.READING_HEADER) {
                    currentTable.header = p
                    state = State.READING_DATA
                }
                else {
                    assert state == State.READING_DATA
                    def data = p.collect {
                        try {
                            Integer.parseInt(it)

                        } catch (NumberFormatException nfe){
                            try {
                                Double.parseDouble(it)
                            } catch (NumberFormatException nfe2){
                                it
                            }
                        }
                    }
                    assert data.size() == currentTable.width
                    currentTable.values.add(data)

                }
            }
        }
        out
    }

    static DecimalFormat formatter = new DecimalFormat("##.####");

    static String format(List values, int l){
        assert values.size()<=l
        (values.collect {
            it instanceof Double ?
                formatter.format(it).replaceAll("[.]", ",") :
                "$it"
        } + (values.size()<l ? [""]*(l-values.size()) : [])).join(";")
    }

    static void dumpCSVs(List<Table> tables, Writer writer){
        def maxWidth = tables.max { it.width }.width
        tables.each { Table t ->
            writer.write(format([t.name], maxWidth)+"\n")
            writer.write(format(t.header, maxWidth)+"\n")
            t.values.each { List<Number> n ->
                writer.write(format(n, maxWidth)+"\n")
            }
            writer.write(format([], maxWidth)+"\n")
        }
    }

    static findBestLeafes(List<Map> row, String quality, List out){
        out.add row.max { it[quality] }
    }

    static void findBestLeafes(Map tree, String quality, List out){
        tree.keySet().sort().each {
            findBestLeafes(tree[it], quality, out)
        }
    }

    static Table findBest(Table table, List<String> groupBy, String quality){
        def results = []
        Table out = new Table("best[$quality](${table.name}|${groupBy.join(", ")})", table.header)
        def groups = table.values.collect {
            [table.header, it].transpose().collectEntries()
        }.groupBy(
            groupBy.collect { { x -> x[it] } }
        )
        findBestLeafes(groups, quality, results)
        out.values = results.collect { r -> out.header.collect { r[it] } }
        out
        //out.values = map flatten on results
    }
}
