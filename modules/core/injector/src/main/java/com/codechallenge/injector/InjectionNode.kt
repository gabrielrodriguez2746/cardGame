package com.codechallenge.injector

interface InjectionNode<T : NodeComponent> {

    fun inject(component: T)
}
