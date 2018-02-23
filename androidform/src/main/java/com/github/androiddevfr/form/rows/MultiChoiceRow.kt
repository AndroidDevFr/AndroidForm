package com.github.androiddevfr.form.rows

import android.content.Context
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.AppCompatRadioButton
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.RelativeLayout
import com.github.androiddevfr.form.core.DimensionUtils
import com.github.androiddevfr.form.core.DimensionUtils.dpToPx

class MultiChoiceRow(context: Context) : AbstractTitleRow<List<String>>(context) {

    private var value: MutableList<String> = mutableListOf<String>()

    var items: List<String> = listOf<String>()
    var defaultChecked: List<String> = listOf<String>()

    var spacingBetweenTitleAndItems = dpToPx(DEFAULT_MARGIN_BOTTOM)

    var customizeMultiChoiceView: (MultiChoiceRow, CompoundButton) -> Unit = { choiceRow, compoundButton -> }

    /**
     * Change this lambda to have a custom MultiChoice View
     */
    fun customizeMultiChoiceView(block: (MultiChoiceRow, CompoundButton) -> Unit) {
        this.customizeMultiChoiceView = block
    }

    init {
        validator = { v -> v != null }
        onCreateView<MultiChoiceRow> {
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
                    val checkableView = createMultiChoiceView(generateRowItemId(), item, defaultChecked.contains(item))
                    checkableView.setOnCheckedChangeListener { buttonView, isChecked ->
                        if(isChecked){
                            value.add(buttonView.text.toString())
                        } else {
                            value.remove(buttonView.text.toString())
                        }
                    }
                    addView(checkableView)
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

    /**
     * Use this lambda to change the visual aspect of the TitleView
     */
    protected fun createMultiChoiceView(viewId: Int, textValue: String,  checkedByDefault: Boolean): CompoundButton {
        val view = AppCompatCheckBox(context).apply {
            text = textValue
            id = viewId
            isChecked = checkedByDefault
        } as CompoundButton
        customizeMultiChoiceView.invoke(this, view)
        return view
    }

    override fun value(): List<String>? {
        return value
    }
}