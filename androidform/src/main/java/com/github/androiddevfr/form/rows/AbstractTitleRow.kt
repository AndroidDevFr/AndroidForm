package com.github.androiddevfr.form.rows

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.widget.ImageView
import android.widget.TextView

abstract class AbstractTitleRow<V>(context: Context) : Row<V>(context) {

    companion object {
        val DEFAULT_MARGIN_TOP = 6
        val DEFAULT_MARGIN_BOTTOM = 8
        val DEFAULT_MARGIN_LEFT = 16
        val DEFAULT_MARGIN_RIGHT = 16

        val TITLE_VIEW_ID = 1
        val ICON_VIEW_ID = 2
        val VALUE_VIEW_ID = 3
    }

    var title: String? = null
    var icon: Drawable? = null

    //will be created by onCreateView
    var titleView: TextView? = null
    var iconView: ImageView? = null

    /**
     * Implementation of the TitleView visual aspect
     */
    protected var customizeTitleView: ((AbstractTitleRow<V>, TextView) -> Unit) = { row, textView ->
        textView.setTextColor(Color.parseColor("#3E3E3E"))
        textView.textSize = 16f
    }

    protected var customizeIconView: ((AbstractTitleRow<V>, ImageView) -> Unit) = { row, imageView ->
    }

    /**
     * Use this lambda to change the visual aspect of the TitleView
     */
    protected fun createTitleView(viewId: Int): TextView {
        titleView = AppCompatTextView(context).apply {
            text = title
            id = viewId
        }

        customizeTitleView.invoke(this, this.titleView as TextView)
        return titleView as TextView
    }

    //TODO into a lambda -> eg: round imageview ?
    protected fun createIconView(viewId: Int): ImageView {
        iconView = AppCompatImageView(context).apply {
            setImageDrawable(icon)
            id = viewId
        }
        customizeIconView.invoke(this, this.iconView as ImageView)
        return iconView as ImageView
    }

    fun <R : AbstractTitleRow<V>> customizeTitleView(block: ((R, TextView) -> Unit)) {
        this.customizeTitleView = block as (AbstractTitleRow<V>, TextView) -> Unit
    }

    fun <R : AbstractTitleRow<V>> customizeIconView(block: ((R, ImageView) -> Unit)) {
        this.customizeIconView = block as (AbstractTitleRow<V>, ImageView) -> Unit
    }

    //override to have a custom behavior on value click (eg: open dialog)
    open fun onValueClicked() {}
}