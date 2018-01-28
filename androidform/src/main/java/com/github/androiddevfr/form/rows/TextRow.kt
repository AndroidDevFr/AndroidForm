package com.github.androiddevfr.form.rows

import android.content.Context
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView

open abstract class AbstractTextRow<V>(context: Context) : Row<V>(context) {
    var title: String? = null
    var placeholder: String? = null

    //will be created by onCreateView
    var titleView: TextView? = null
    //will be created by onCreateView
    var placeholderView: TextView? = null

    //override to have a custom behavior on value click (eg: open dialog)
    open fun onValueClicked(){}
}

open class TextRow(context: Context) : AbstractTextRow<String>(context) {

    //will be created by onCreateView
    var editView: EditText? = null

    override fun value(): String? {
        return editView?.text.toString()
    }

    init {
        validator = { v -> v != null && v.isNotEmpty() }
        onCreateView<TextRow>{
            RelativeLayout(context) //TODO create view
        }
    }
}

