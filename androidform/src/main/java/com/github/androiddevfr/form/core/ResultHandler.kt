package com.github.androiddevfr.form.core

class ResultHandler<V> {
    private val listeners = mutableSetOf<(V) -> Unit>()

    fun registerListener(listener: (V) -> Unit){
        listeners.add(listener)
    }

    fun onValue(value : V){
        listeners.forEach{
            it.invoke(value)
        }
    }
}