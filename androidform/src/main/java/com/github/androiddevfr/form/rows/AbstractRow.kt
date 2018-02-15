package com.github.androiddevfr.form.rows

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatTextView
import android.widget.TextView

abstract class AbstractRow<V>(context: Context) : Row<V>(context) {

    companion object {
        val DEFAULT_MARGIN_TOP = 6
        val DEFAULT_MARGIN_BOTTOM = 6
        val DEFAULT_MARGIN_LEFT = 16
        val DEFAULT_MARGIN_RIGHT = 16
    }

    var title: String? = null

    //will be created by onCreateView
    var titleView: TextView? = null

    /**
     * Implementation of the TitleView visual aspect
     */
    protected var customizeTitleView: ((AbstractRow<V>, TextView) -> Unit) = { row, textView ->
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

    fun <R : AbstractRow<V>> customizeTitleView(block: ((R, TextView) -> Unit)) {
        this.customizeTitleView = block as (AbstractRow<V>, TextView) -> Unit
    }

    //override to have a custom behavior on value click (eg: open dialog)
    open fun onValueClicked() {}
}