package com.github.filipmalczak.learningsystems.lab5_knn

import com.github.filipmalczak.learningsystems.model.DataSet

import static java.lang.Math.*


class Distance {
    static Closure<Double> euclidean() {
        { List<Number> v1, List<Number> v2 ->
            assert v1.size() == v2.size()
            double out = 0.0;
            v1.eachWithIndex { Number x, int idx ->
                def delta = x - v2[idx]
                out += delta**2
            }
            sqrt(out)
        }
    }

    static Closure<Double> manhattan() {
        { List<Number> v1, List<Number> v2 ->
            assert v1.size() == v2.size()
            double out = 0.0;
            v1.eachWithIndex { Number x, int idx ->
                def delta = abs(x - v2[idx])
                out += delta
            }
            out
        }
    }

    //todo: do experiments for m=3, m=5
    static Closure<Double> minkowsky(int m) {
        { List<Number> v1, List<Number> v2 ->
            assert v1.size() == v2.size()
            double out = 0.0;
            v1.eachWithIndex { Number x, int idx ->
                def delta = abs(x - v2[idx])
                out += delta**m
            }
            out
        }
    }

    static Closure<Double> chebyshev() {
        { List<Number> v1, List<Number> v2 ->
            assert v1.size() == v2.size()
            double out = 0.0;
            v1.eachWithIndex { Number x, int idx ->
                def delta = abs(x - v2[idx])
                if (delta>out)
                    out = delta;
            }
            out
        }
    }

    static Closure<Double> mahalanobis(DataSet space) {
        //todo: implement this
    }
}