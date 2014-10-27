package com.github.filipmalczak.learningsystems.common

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@CompileStatic
@Slf4j
class ARFFLoader {

    private void parseAttribute(String line, List<String> names, Map<String, List<String>> domains) {
//        def parts = line.split(/\s+/)
        assert line.toLowerCase().startsWith("attribute")
        line = line.substring("attribute".size()).trim()
        def separatorIdx = line.indexOf(" ")
        def name = line.substring(0, separatorIdx).trim()
        def declaration = line.substring(separatorIdx).trim()
        names.add(name)
        // if typedef doesn't start with "{" we assume its numeric
//        assert declaration.toLowerCase() == "numeric" || declaration.startsWith("{")
        if (declaration.startsWith("{")) {
            assert declaration.endsWith("}")
            declaration = declaration.substring(1, declaration.size()-1)
            def domain = declaration.split(/,/)
            domains[name] = domain.collect { String value -> value.trim() }
        }
    }

    private List<List> parseData(BufferedReader reader, List<String> names, Map<String, List<String>> domains){
        def out = []
        reader.eachLine { String line ->
            def values = line.split(/,/)
            assert values.size() == names.size()
            def vector = []
            values.each { String value ->
                try {
                    vector.add new Double(value)
                } catch (NumberFormatException e) {
                    assert domains[value].contains(value)
                    vector.add value
                }
                out.add vector
            }
        }
        return out
    }

    DataSet load(BufferedReader reader){
        String name = null
        List<String> names = []
        Map<String, List<String>> domains = [:]
        List<List> instances = null
        String line = null
        while ((line = reader.readLine()) != null){
            log.debug("${line.class}")
            log.debug("Line: $line")
            if (line) {
                line = line.trim()
                switch (line[0]) {
                    case null:
                    case "%": break
                    case "@":
                        line = line.substring(1)
                        if (line.toLowerCase().startsWith("relation")) {
                            assert name == null
                            name = line.substring("relation".size()).trim()
                        } else if (line.toLowerCase().startsWith("attribute")) {
                            parseAttribute(line, names, domains)
                        } else if (line.toLowerCase().startsWith("data")) {
                            assert instances == null
                            instances = parseData(reader, names, domains)
                        }
                        break
                    default: assert "Either line is either empty or it starts with @ or % - this does not!" && line =="xyz "&& false
                }
            }
        }
    }
}
