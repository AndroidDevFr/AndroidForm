package com.github.androiddevfr.form

abstract class Row<V>() {
    var id: Int = -1

    var validator: (V?) -> Boolean = { v -> false }

    abstract fun value() : V?

}