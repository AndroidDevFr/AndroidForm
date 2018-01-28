package com.github.androiddevfr.form.rows

import android.content.Context
import android.util.Patterns
import android.widget.RelativeLayout

open class PhoneRow(context: Context) : TextRow(context) {

    init {
        validator = { v -> Patterns.PHONE.matcher(v).matches() }
        onCreateView<PhoneRow>{
            RelativeLayout(context) //TODO create view
        }
    }

}