package com.github.filipmalczak.learningsystems.lab5_knn

import com.github.filipmalczak.learningsystems.model.Instance

import static java.lang.Math.exp


class ResultInput {
    static Closure<Double> constant = {
        Instance neighbour, Instance classified, Collection<Instance> neighbourhood, Closure<Double> distance ->
            1
    }

    static Closure<Double> proportional = {
        Instance neighbour, Instance classified, Collection<Instance> neighbourhood, Closure<Double> distance ->
            100.0/distance.call(neighbour.valuesWithoutClass, classified.valuesWithoutClass)
    }

    static Closure<Double> weightedWithDistance = {
        Instance neighbour, Instance classified, Collection<Instance> neighbourhood, Closure<Double> distance ->
            def sumOfDistExp = neighbourhood.sum { distance(classified.valuesWithoutClass, it.valuesWithoutClass) }
            sumOfDistExp / distance.call(classified.valuesWithoutClass, neighbour.valuesWithoutClass)
    }

    static Closure<Double> weightedWithExp = {
        Instance neighbour, Instance classified, Collection<Instance> neighbourhood, Closure<Double> distance ->
            def sumOfDistExp = neighbourhood.sum { exp(distance(classified.valuesWithoutClass, it.valuesWithoutClass)) }
            sumOfDistExp / exp(distance.call(classified.valuesWithoutClass, neighbour.valuesWithoutClass))
    }

    static Closure<Double> weightedWithSquare = {
        Instance neighbour, Instance classified, Collection<Instance> neighbourhood, Closure<Double> distance ->
            def sumOfDistExp = neighbourhood.sum { (distance(classified.valuesWithoutClass, it.valuesWithoutClass))**2 }
            sumOfDistExp / (distance.call(classified.valuesWithoutClass, neighbour.valuesWithoutClass))**2
    }
}
