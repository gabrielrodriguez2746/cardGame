package com.codechallenge.injector

interface DependedNodeComponentFactory<T : NodeComponent, R : NodeComponentDependencies> :
    NodeComponentFactory<T> {
    fun create(dependencies: R): T
}
