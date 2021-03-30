package com.codechallenge.injector

interface InjectionNode<T : NodeComponent> {
    val identifier: String
    fun inject(component: NodeComponent)
}
