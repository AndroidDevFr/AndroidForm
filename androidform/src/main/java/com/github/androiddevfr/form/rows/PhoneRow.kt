package com.github.androiddevfr.form.rows

import android.content.Context
import android.util.Patterns
import android.view.inputmethod.EditorInfo

open class PhoneRow(context: Context) : TextRow(context) {

    init {
        validator = { v -> Patterns.PHONE.matcher(v).matches() }
        addOnViewCreatedListener<PhoneRow> {
            editView?.inputType = EditorInfo.TYPE_CLASS_PHONE
        }
    }

}