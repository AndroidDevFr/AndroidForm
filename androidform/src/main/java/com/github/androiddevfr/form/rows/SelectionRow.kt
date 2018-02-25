package com.github.androiddevfr.form.rows

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatSpinner
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.androiddevfr.form.core.DimensionUtils
import com.github.androiddevfr.form.core.DimensionUtils.dpToPx


class SelectionRow(context: Context) : AbstractTitleRow<String?>(context) {

    companion object {
        val DEFAULT_TITLE_MARGIN_BOTTOM = 14
        val DEFAULT_PADDING_ROW_VERTICAL = 8
        val DEFAULT_PADDING_ROW_HORIZONTAL = 8
    }

    private var value: String? = null

    var items: List<String> = listOf<String>()
    var defaultItem: String? = null
    var spinnerRowLayoutId = android.R.layout.simple_spinner_item

    var customizeSelectionView: (SelectionRow, Spinner) -> Unit = { choiceRow, spinner ->

    }

    var customizeSelectionItemView: (SelectionRow, View, String) -> Unit = { choiceRow, row, value ->
        val label = row as TextView
        row.setPadding(dpToPx(DEFAULT_PADDING_ROW_HORIZONTAL), dpToPx(DEFAULT_PADDING_ROW_VERTICAL), dpToPx(DEFAULT_PADDING_ROW_HORIZONTAL), dpToPx(DEFAULT_PADDING_ROW_VERTICAL))
        label.setTextColor(Color.BLACK)
        label.text = value
    }

    var spacingBetweenTitleAndItems = DimensionUtils.dpToPx(DEFAULT_TITLE_MARGIN_BOTTOM)


    /**
     * Change this lambda to have a custom Selection View
     */
    fun customizeSelectionView(block: (SelectionRow, Spinner) -> Unit) {
        this.customizeSelectionView = block
    }

    /**
     * Change this lambda to have a custom Selection Item View
     */
    fun customizeSelectionItemView(block: (SelectionRow, View, String) -> Unit) {
        this.customizeSelectionItemView = block
    }

    init {
        onCreateView<SelectionRow> {
            LinearLayout(context).apply {

                addView(
                        createTitleView(TITLE_VIEW_ID).apply {
                            titleView = this
                        },
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                            bottomMargin = spacingBetweenTitleAndItems
                        })

                var defaultIndex: Int? = null
                val defaultItemValue: String? = defaultItem
                if (defaultItemValue != null) {
                    defaultIndex = items.indexOf(defaultItemValue)
                }
                addView(createSelectionView(generateRowItemId(), items, defaultIndex))

                setPadding(
                        DimensionUtils.dpToPx(DEFAULT_MARGIN_LEFT),
                        DimensionUtils.dpToPx(DEFAULT_MARGIN_TOP),
                        DimensionUtils.dpToPx(DEFAULT_MARGIN_RIGHT),
                        DimensionUtils.dpToPx(DEFAULT_MARGIN_BOTTOM)
                )
            }
        }
    }

    private fun createSelectionView(viewId: Int, items: List<String>, defaultIndex: Int?): Spinner {
        val view = AppCompatSpinner(context).apply {
            id = viewId
        } as Spinner

        view.adapter = object : ArrayAdapter<String>(context, spinnerRowLayoutId, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val superView = super.getView(position, convertView, parent)
                customizeSelectionItemView.invoke(this@SelectionRow, superView, this.getItem(position))
                return superView
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val superView = super.getDropDownView(position, convertView, parent)
                customizeSelectionItemView.invoke(this@SelectionRow, superView, this.getItem(position))
                return superView
            }
        }

        view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                value = items[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                value = null
            }
        }

        if (defaultIndex != null) {
            view.setSelection(defaultIndex)
        }
        customizeSelectionView.invoke(this, view)
        return view
    }

    override fun value(): String? {
        return value
    }

}