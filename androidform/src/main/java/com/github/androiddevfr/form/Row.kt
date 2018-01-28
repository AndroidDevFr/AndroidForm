package com.github.androiddevfr.form

import android.content.Context
import android.view.View

abstract class Row<V>(val context: Context) {
    var id: Int = -1

    var validator: ((V?) -> Boolean) = { v -> false }

    abstract fun value() : V?

    /**
     * Overriden by rows implementations, will return the row's view,
     * Will be added into the Section
     */
    abstract fun onCreateView() : View
}