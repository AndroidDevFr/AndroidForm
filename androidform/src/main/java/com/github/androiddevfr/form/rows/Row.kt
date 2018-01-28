package com.github.androiddevfr.form.rows

import android.content.Context
import android.view.View

abstract class Row<V>(val context: Context) {
    var id: Int = -1

    var validator: ((V?) -> Boolean) = { v -> false }

    private val valueChangeListeners = mutableListOf<((V?) -> Unit)>()

    /**
     * Will contain the view, created by onCreateView()
     */
    var view: View? = null

    /**
     * Will be used to get the row's value,
     * after the user's input
     */
    abstract fun value() : V?

    fun addValueChangeListener(listener: (V?) -> Unit){
        valueChangeListeners.add(listener)
    }

    /**
     * Ping all listeners when the row value has changed
     * Has to be called from row's implementations
     */
    protected fun onValueChange(){
        val value = value()
        valueChangeListeners.forEach{
            it.invoke(value)
        }
    }

    /**
     * Overriden by rows implementations, will return the row's view,
     * Will be added into the Section
     */
    abstract fun onCreateView() : View
}