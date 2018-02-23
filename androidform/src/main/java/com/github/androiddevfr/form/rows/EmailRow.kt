package com.github.androiddevfr.form.rows

import android.content.Context
import android.support.v7.content.res.AppCompatResources
import android.util.Patterns
import com.github.androiddevfr.form.R
import com.github.androiddevfr.form.rows.enum.TextType

open class EmailRow(context: Context) : TextRow(context) {

    init {
        validator = { v -> Patterns.EMAIL_ADDRESS.matcher(v).matches() }
        inputType = TextType.Email
        icon = AppCompatResources.getDrawable(context, R.drawable.ic_email_black_24dp)
    }

}