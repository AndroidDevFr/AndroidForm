package com.github.androiddevfr.form.rows

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.androiddevfr.form.core.DimensionUtils

open abstract class AbstractTextRow<V>(context: Context) : Row<V>(context) {
    var title: String? = null

    //will be created by onCreateView
    var titleView: TextView? = null

    /**
     * Implementation of the TitleView visual aspect
     */
    protected var customizeTitleView: ((AbstractTextRow<V>, TextView) -> Unit) = { row, textView ->
        textView.setTextColor(Color.parseColor("#3E3E3E"))
        textView.textSize = 16f
    }

    /**
     * Use this lambda to change the visual aspect of the TitleView
     */
    protected fun createTitleView(): TextView {
        titleView = AppCompatTextView(context)
        titleView?.text = title
        customizeTitleView.invoke(this, this.titleView as TextView)
        return titleView as TextView
    }

    fun <R : AbstractTextRow<V>> customizeTitleView(block: ((R, TextView) -> Unit)) {
        this.customizeTitleView = block as (AbstractTextRow<V>, TextView) -> Unit
    }

    //override to have a custom behavior on value click (eg: open dialog)
    open fun onValueClicked() {}
}

open class TextRow(context: Context) : AbstractTextRow<String>(context) {

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
            editTextLayoutParams.leftMargin = DimensionUtils.doToPx(16f)
            editTextLayoutParams.topMargin = DimensionUtils.doToPx(16f)
            editTextLayoutParams.bottomMargin = DimensionUtils.doToPx(16f)
            editTextLayoutParams.rightMargin = DimensionUtils.doToPx(16f)
            editTextLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            editTextLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            editView?.layoutParams = editTextLayoutParams
            layout.addView(editView)

            //Generated the Title
            createTitleView()
            val titleLayoutParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            titleLayoutParams.leftMargin = DimensionUtils.doToPx(16f)
            titleLayoutParams.topMargin = DimensionUtils.doToPx(16f)
            titleLayoutParams.bottomMargin = DimensionUtils.doToPx(16f)
            titleLayoutParams.rightMargin = DimensionUtils.doToPx(16f)
            titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            titleView?.layoutParams = titleLayoutParams
            layout.addView(titleView)

            layout
        }
    }
}

