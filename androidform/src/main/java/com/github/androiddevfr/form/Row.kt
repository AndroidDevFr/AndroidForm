package com.github.androiddevfr.form

import android.content.Context

abstract class Row<V>(val context: Context) {
    var id: Int = -1

    var validator: (V?) -> Boolean = { v -> false }

    abstract fun value() : V?

}