package com.github.androiddevfr.form.rows.enum

import android.text.InputType
import android.view.inputmethod.EditorInfo

enum class TextType{
    Text {
        override fun editorInfo(): Int {
            return InputType.TYPE_CLASS_TEXT;
        }
    },
    Email{
        override fun editorInfo(): Int {
            return InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
    },
    Phone{
        override fun editorInfo(): Int {
            return EditorInfo.TYPE_CLASS_PHONE;
        }
    };

    abstract fun editorInfo(): Int

}