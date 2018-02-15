package com.github.androiddevfr.form.rows

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatEditText
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import android.widget.RelativeLayout
import com.github.androiddevfr.form.core.DimensionUtils

open class TextRow(context: Context) : AbstractTitleRow<String>(context) {

    //will be created by onCreateView
    var editView: EditText? = null
    var placeholder: String = ""

    override fun value(): String? {
        return editView?.text.toString()
    }

    /**
     * Implementation of the EditText visual aspect
     */
    protected var customizeEditText: ((TextRow, EditText) -> Unit) = { row, editText ->
        editText.setTextColor(Color.parseColor("#3E3E3E"))
        editText.textSize = 16f
    }

    /**
     * Use this lambda to change the visual aspect of the EditText
     */
    fun customizeEditText(block: ((TextRow, EditText) -> Unit)) {
        this.customizeEditText = block
    }

    protected fun createEditText(): EditText {
        editView = AppCompatEditText(context)
        editView?.hint = placeholder
        customizeEditText.invoke(this, this.editView as EditText)
        return editView as EditText
    }

    init {
        validator = { v -> v != null && v.isNotEmpty() }
        onCreateView<TextRow> {
            val layout = RelativeLayout(context)

            //Generated the EditText
            createEditText()
            val editTextLayoutParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            editTextLayoutParams.leftMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_LEFT)
            editTextLayoutParams.topMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_TOP)
            editTextLayoutParams.bottomMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_BOTTOM)
            editTextLayoutParams.rightMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_RIGHT)
            editTextLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            editTextLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            editView?.layoutParams = editTextLayoutParams
            layout.addView(editView)

            //Generated the Title
            createTitleView()
            val titleLayoutParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            titleLayoutParams.leftMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_LEFT)
            titleLayoutParams.topMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_TOP)
            titleLayoutParams.bottomMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_BOTTOM)
            titleLayoutParams.rightMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_RIGHT)
            titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            titleView?.layoutParams = titleLayoutParams
            layout.addView(titleView)

            layout
        }
    }
}

