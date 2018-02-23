package com.github.androiddevfr.form.rows

import android.content.Context
import android.graphics.Color
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
        this.editView = EditText(context).apply {
            id = VALUE_VIEW_ID
            hint = placeholder
        }


        customizeEditText.invoke(this, this.editView as EditText)
        return editView as EditText
    }

    init {
        validator = { v -> v != null && v.isNotEmpty() }
        onCreateView<TextRow> {
            val layout = RelativeLayout(context)

            //Generated the EditText
            editView = createEditText(VALUE_VIEW_ID)

            //Generated the Title
            titleView = createTitleView(TITLE_VIEW_ID)

            //Generated the Icon
            if (icon != null) {
                iconView = createIconView(ICON_VIEW_ID)
            }

            val titleLayoutParams = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                leftMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_LEFT)
                topMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_TOP)
                bottomMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_BOTTOM)
                rightMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_RIGHT)

                addRule(RelativeLayout.CENTER_VERTICAL)
            }

            if (iconView != null) {
                titleLayoutParams.leftMargin = 0

                titleLayoutParams.addRule(RelativeLayout.RIGHT_OF, iconView!!.id)
                layout.addView(iconView, RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    leftMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_LEFT)
                    topMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_TOP)
                    bottomMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_BOTTOM)
                    rightMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_RIGHT)

                    addRule(RelativeLayout.CENTER_VERTICAL)
                    addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                })
            } else {
                titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            }

            layout.addView(editView, RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                setMargins(
                        DimensionUtils.dpToPx(DEFAULT_MARGIN_LEFT),
                        DimensionUtils.dpToPx(DEFAULT_MARGIN_TOP),
                        DimensionUtils.dpToPx(DEFAULT_MARGIN_BOTTOM),
                        DimensionUtils.dpToPx(DEFAULT_MARGIN_RIGHT)

                )
                addRule(RelativeLayout.CENTER_VERTICAL)
                addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            })
            layout.addView(titleView, titleLayoutParams)

            layout
        }
    }
}

