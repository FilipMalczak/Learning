package com.github.filipmalczak.learningsystems.model

import groovy.transform.Canonical
import groovy.transform.CompileStatic

@CompileStatic
@Canonical
class DataSet {
    String name
    DataScheme scheme
    @Delegate List<List> data

    List<Instance> getInstances(){
        data.collect { List vector -> new Instance(vector, scheme) }
    }

    Instance getInstance(int i){
        new Instance(data[i], scheme)
    }

    List getAttributeSnapshot(int attrIdx){
        return data.collect { it[attrIdx] }
    }

    List getAttributeSnapshot(String attrName){
        return getAttributeSnapshot(scheme.getAttributeIndex(attrName))
    }

    /**
     * ~= subset of data with idxs <floor(size*from), ceiling(size*to)> where size is data.size()
     */
    DataSet subset(double from, double to){
        return new DataSet(
            name+"-subset($from; $to)",
            scheme,
            data[Math.floor(from*data.size())..Math.ceil(to*data.size())]
        )
    }

    /**
     * ~= subset of data with idxs <from, to>
     */
    DataSet subset(int from, int to){
        return new DataSet(
            name+"-subset($from; $to)",
            scheme,
            data[from..to]
        )
    }

    /**
     * classValIdx is number of class name in class domain, not number of class attribute.
     */
    List<Instance> getInstancesForClass(int classValIdx){
        getInstancesForClass(scheme.classDomain[classValIdx])
    }

    /**
     * classValueName is value of class attribute, not attributes name.
     */
    List<Instance> getInstancesForClass(String classValueName){
        instances.findAll { it.classValue == classValueName } as List<Instance>
    }

    private int copies = 0

    DataSet getEmptyCopy(){
        new DataSet("${this.name}:copy#${copies++}", scheme, [])
    }

    DataSet getFullCopy(){
        new DataSet("${this.name}:copy#${copies++}", scheme, data.collect())
    }

    int getSize(){
        data.size()
    }

    DataSet plus(DataSet another){
        assert this.scheme == another.scheme
        new DataSet("${this.name}+${another.name}".toString(), scheme, (this.data + another.data) as List<List>)
    }

    DataSet getCleanSet(){
        def out = fullCopy
        instances.each { Instance i ->
            def duplicate = instances.find { Instance j ->
                !j.equals(i) && j.modified(i.className, i.classValue).equals(i)
            }
            if (duplicate!=null) {
                out.data.remove(i.values)
                out.data.remove(duplicate)
            }
        }
        out
    }

    DataSet getSample(double sizeFactor, boolean withReturning=true){
        assert sizeFactor>0.0 && sizeFactor<=1.0
        getSample((data.size()*sizeFactor) as int, withReturning)
    }

    DataSet getSample(int size, boolean withReturning=true){
        Random random = new Random()
        Collection<Integer> idxs = (withReturning ? [] as List<Integer> : [] as Set<Integer>)
        while (idxs.size()<size)
            idxs.add random.nextInt(data.size())
        new DataSet(
            "$name-sample",
            scheme,
            idxs.collect { data[it] }
        )
    }
}
