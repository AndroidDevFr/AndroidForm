package com.github.androiddevfr.form.rows

import android.content.Context
import android.support.v7.widget.AppCompatRadioButton
import android.view.ViewGroup
import android.widget.*
import com.github.androiddevfr.form.core.DimensionUtils
import com.github.androiddevfr.form.core.DimensionUtils.dpToPx

class SingleChoiceRow(context: Context) : AbstractTitleRow<String?>(context) {

    companion object {
        val DEFAULT_TITLE_MARGIN_BOTTOM = 14
    }

    private var value: String? = null

    var items: List<String> = listOf<String>()
    var defaultChecked: List<String> = listOf<String>()

    var customizeSingleChoiceView: (SingleChoiceRow, CompoundButton) -> Unit = { choiceRow, compoundButton ->

    }

    var spacingBetweenTitleAndItems = dpToPx(DEFAULT_TITLE_MARGIN_BOTTOM)


    /**
     * Change this lambda to have a custom SingleChoice View
     */
    fun customizeSingleChoiceView(block: (SingleChoiceRow, CompoundButton) -> Unit) {
        this.customizeSingleChoiceView = block
    }

    init {
        onCreateView<SingleChoiceRow> {
            RadioGroup(context).apply {
                orientation = LinearLayout.VERTICAL

                addView(
                        createTitleView(TITLE_VIEW_ID).apply {
                            titleView = this
                        },
                        RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                            bottomMargin = spacingBetweenTitleAndItems
                        })

                items.forEach { item ->
                    addView(createSingleChoiceView(generateRowItemId(), item, defaultChecked.contains(item)))
                }

                setOnCheckedChangeListener { buttonView, checkedId ->
                    value = buttonView.findViewById<CompoundButton>(checkedId).text.toString()
                }

                setPadding(
                        DimensionUtils.dpToPx(DEFAULT_MARGIN_LEFT),
                        DimensionUtils.dpToPx(DEFAULT_MARGIN_TOP),
                        DimensionUtils.dpToPx(DEFAULT_MARGIN_RIGHT),
                        DimensionUtils.dpToPx(DEFAULT_MARGIN_BOTTOM)
                )
            }
        }
    }

    private fun createSingleChoiceView(viewId: Int, textValue: String, checkedByDefault: Boolean): CompoundButton {
        val view = AppCompatRadioButton(context).apply {
            text = textValue
            id = viewId
            isChecked = checkedByDefault
        } as CompoundButton
        customizeSingleChoiceView.invoke(this, view)
        return view
    }

    override fun value(): String? {
        return value
    }
}