package com.codechallenge.injector

interface SaveStateInjectionNode<T : NodeComponent> : InjectionNode<T> {
    var saveState: Boolean
}
