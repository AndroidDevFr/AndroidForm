package com.github.androiddevfr.form.rows

import android.content.Context
import android.util.Patterns
import android.view.View
import android.widget.RelativeLayout

open class PhoneRow(context: Context) : TextRow(context) {

    init {
        validator = { v -> Patterns.PHONE.matcher(v).matches() }
    }

    override fun onCreateView() : View {
        return RelativeLayout(context) //TODO create view
    }

}