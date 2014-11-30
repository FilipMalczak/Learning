package com.github.filipmalczak.learningsystems.model.discretization

import com.github.filipmalczak.learningsystems.model.DataSet

public interface Discretizer {

    public List<Number> getCuts(int attrIdx, DataSet dataSet)
    public DataSet discretize(DataSet dataSet)







}