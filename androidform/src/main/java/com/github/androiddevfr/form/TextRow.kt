package com.github.androiddevfr.form

import android.util.Patterns
import android.widget.EditText
import java.util.*

open abstract class AbstractTextRow<V> : Row<V>() {
    var title: String? = null
    var placeholder: String? = null
}

open class TextRow : AbstractTextRow<String>() {

    lateinit var edit: EditText

    override fun value(): String? {
        return edit.text.toString()
    }

    init {
        validator = { v -> v != null && v.isNotEmpty() }
    }

}

open class PhoneRow : TextRow(){

    init {
        validator = { v -> Patterns.PHONE.matcher(v).matches() }
    }

}

open class DateRow : AbstractTextRow<Date>(){

    init {
        validator = { v -> v != null }
    }

    var value: Date? = null

    private var clickAction: ((DateRow) -> Date)? = null

    fun customizePickDate(callback: ((DateRow) -> Date)): DateRow {
        this.clickAction = callback
        return this
    }

    override fun value(): Date? {
        return value
    }
}

