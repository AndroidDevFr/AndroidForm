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

    companion object {
        fun <V> create(block: ((ResultHandler<V>) -> Unit)) : ResultHandler<V> {
            val resultHandler = ResultHandler<V>()
            block.invoke(resultHandler)
            return resultHandler
        }
    }
}