package com.codechallenge.injector

interface IndependentNodeComponentFactory<T : NodeComponent> : NodeComponentFactory<T> {
    fun create(): T
}
