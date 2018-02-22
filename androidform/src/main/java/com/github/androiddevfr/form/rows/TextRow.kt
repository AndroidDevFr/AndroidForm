package com.github.androiddevfr.form.rows

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatEditText
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import android.widget.RelativeLayout
import com.github.androiddevfr.form.core.DimensionUtils
import com.github.androiddevfr.form.rows.enum.TextType

open class TextRow(context: Context) : AbstractTitleRow<String>(context) {

    //will be created by onCreateView
    var editView: EditText? = null
    var placeholder: String = ""
    var inputType: TextType = TextType.Text
        set(value) {
            field = value
            editView?.inputType = value.editorInfo()
        }


    override fun value(): String? {
        return editView?.text.toString()
    }

    /**
     * Implementation of the EditText visual aspect
     */
    protected var customizeEditText: ((TextRow, EditText) -> Unit) = { row, editText ->
        editText.setTextColor(Color.parseColor("#3E3E3E"))
        editView?.inputType = inputType.editorInfo()
        editText.textSize = 16f
    }

    /**
     * Use this lambda to change the visual aspect of the EditText
     */
    fun customizeEditText(block: ((TextRow, EditText) -> Unit)) {
        this.customizeEditText = block
    }

    protected fun createEditText(viewId: Int): EditText {
        editView = AppCompatEditText(context)
        editView!!.id = VALUE_VIEW_ID
        editView!!.hint = placeholder
        customizeEditText.invoke(this, this.editView as EditText)
        return editView as EditText
    }

    init {
        validator = { v -> v != null && v.isNotEmpty() }
        onCreateView<TextRow> {
            val layout = RelativeLayout(context)

            val iconLayoutParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            val editTextLayoutParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            val titleLayoutParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

            //Generated the EditText
            editView = createEditText(VALUE_VIEW_ID)
            layout.addView(editView)

            //Generated the Title
            titleView = createTitleView(TITLE_VIEW_ID)
            layout.addView(titleView)

            //Generated the Icon
            if (icon != null) {
                iconView = createIconView(ICON_VIEW_ID)
            }


            editTextLayoutParams.leftMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_LEFT)
            editTextLayoutParams.topMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_TOP)
            editTextLayoutParams.bottomMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_BOTTOM)
            editTextLayoutParams.rightMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_RIGHT)
            editTextLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            editTextLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)

            titleLayoutParams.leftMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_LEFT)
            titleLayoutParams.topMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_TOP)
            titleLayoutParams.bottomMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_BOTTOM)
            titleLayoutParams.rightMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_RIGHT)
            titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)

            iconLayoutParams.leftMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_LEFT)
            iconLayoutParams.topMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_TOP)
            iconLayoutParams.bottomMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_BOTTOM)
            iconLayoutParams.rightMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_RIGHT)
            iconLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            iconLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)

            if (iconView != null) {
                titleLayoutParams.leftMargin = 0
                titleLayoutParams.addRule(RelativeLayout.RIGHT_OF, iconView!!.id)
                layout.addView(iconView)
            } else {
                titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            }

            editView?.layoutParams = editTextLayoutParams
            iconView?.layoutParams = iconLayoutParams
            titleView?.layoutParams = titleLayoutParams

            layout
        }
    }
}

