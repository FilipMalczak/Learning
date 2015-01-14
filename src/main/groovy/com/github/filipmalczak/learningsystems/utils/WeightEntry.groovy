package com.github.filipmalczak.learningsystems.utils

import groovy.transform.Canonical


@Canonical
class WeightEntry<T> {
    T classifier
    Map<String, Double> weights
}
