package com.github.androiddevfr.form.rows

import android.content.Context
import android.view.View

abstract class Row<V>(val context: Context) {
    var id: Int = -1

    var validator: ((V?) -> Boolean) = { v -> false }

    private val valueChangeListeners = mutableListOf<((Row<V>, V?) -> Unit)>()
    private val viewCreatedListeners = mutableListOf<((Row<V>) -> Unit)>()

    var onCreateView:((Row<V>) -> View) = {
        View(context)
    }

    /**
     * Will contain the view, created by onCreateView()
     */
    var view: View? = null

    /**
     * Will be used to get the row's value,
     * after the user's input
     */
    abstract fun value() : V?

    /**
     * Ping all listeners when the row value has changed
     * Has to be called from row's implementations
     */
    protected fun onValueChange(){
        val value = value()
        valueChangeListeners.forEach{
            it.invoke(this, value)
        }
    }

    fun <R : Row<V>> onCreateView(block: ((R) -> View)) {
        this.onCreateView = block as (Row<V>) -> View
    }

    fun <R : Row<V>> addOnViewCreatedListener(listener: ((R) -> Unit)) {
        viewCreatedListeners.add(listener as (Row<V>) -> Unit)
    }

    fun <R : Row<V>> addValueChangeListener(listener: (R, V?) -> Unit){
        valueChangeListeners.add(listener as (Row<V>, V?) -> Unit)
    }

    /**
     * Called by Section/Form
     * Will create the view
     */
    fun create(){
        this.view = onCreateView.invoke(this)
        viewCreatedListeners.forEach{
            it.invoke(this)
        }
    }
}