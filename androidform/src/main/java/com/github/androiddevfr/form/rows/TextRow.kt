package com.github.androiddevfr.form.rows

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout

open abstract class AbstractTextRow<V>(context: Context) : Row<V>(context) {
    var title: String? = null
    var placeholder: String? = null

    //override to have a custom behavior on value click (eg: open dialog)
    open fun onValueClicked(){}
}

open class TextRow(context: Context) : AbstractTextRow<String>(context) {

    lateinit var edit: EditText

    override fun value(): String? {
        return edit.text.toString()
    }

    init {
        validator = { v -> v != null && v.isNotEmpty() }
    }

    override fun onCreateView() : View {
        return RelativeLayout(context) //TODO create view
    }

}

