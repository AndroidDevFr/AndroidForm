package com.github.androiddevfr.form.section

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import com.github.androiddevfr.form.core.DimensionUtils
import com.github.androiddevfr.form.core.DimensionUtils.dpToPx
import com.github.androiddevfr.form.rows.DateRow
import com.github.androiddevfr.form.rows.PhoneRow
import com.github.androiddevfr.form.rows.Row
import com.github.androiddevfr.form.rows.SeekBarRow
import com.github.androiddevfr.form.rows.TextRow

class Section(private val context: Context, var title: String) {

    companion object {
        val TITLE_DEFAULT_MARGIN_TOP = 6
        val TITLE_DEFAULT_MARGIN_BOTTOM = 6
        val TITLE_DEFAULT_MARGIN_LEFT = 16
        val TITLE_DEFAULT_MARGIN_RIGHT = 16
    }

    var id = -1
    var titleTextColor = Color.parseColor("#2196F3")
    var rowDividerColor = Color.TRANSPARENT
    var rowDividerHeight = dpToPx(1)

    var titleView: View? = null

    val rows = mutableListOf<Row<*>>()

    /**
     * Add a row with title/placeholder and an EditText
     *
     * ---------------------------------
     * | TITLE                         |
     * |                      EDITTEXT |
     * | PLACEHOLDER                   |
     * ---------------------------------
     */
    fun textRow(block: (TextRow.() -> Unit)): Section {
        return row(TextRow(context), block)
    }

    /**
     * Add a row with title/placeholder and an EditText(phone)
     *
     * ---------------------------------
     * | TITLE                         |
     * |                        PHONE  |
     * | PLACEHOLDER                   |
     * ---------------------------------
     */
    fun phoneRow(block: (PhoneRow.() -> Unit)): Section {
        return row(PhoneRow(context), block)
    }

    /**
     * Add a row with title/placeholder and an DatePicher)
     *
     * ---------------------------------
     * | TITLE                         |
     * |                        DATE   | -> Open Date Picker
     * | PLACEHOLDER                   |
     * ---------------------------------
     */
    fun dateRow(block: (DateRow.() -> Unit)): Section {
        return row(DateRow(context), block)
    }

    /**
     * Add a row with title/placeholder and a SeekBar
     *
     * ---------------------------------
     * | TITLE                         |
     * |                     -----O--  |
     * | PLACEHOLDER                   |
     * ---------------------------------
     */
    fun seekBarRow(block: (SeekBarRow.() -> Unit)): Section {
        return row(SeekBarRow(context), block)
    }

    fun <R : Row<*>> row(row: R, block: (R.() -> Unit)): Section {
        rows.add(row)
        block.invoke(row)
        return this
    }

    /**
     * Inline setter for Java usage
     */
    fun id(id: Int): Section {
        this.id = id
        return this
    }

    fun onCreateView(): View {
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        titleView = createSectionTitleView()
        layout.addView(titleView, titleView?.layoutParams)

        for (index in 0 until rows.size) {
            val row = rows[index]
            row.create()
            layout.addView(row.view)
            if (index != rows.size - 1) {
                layout.addView(onCreateDivider(index))
            }
        }

        layout.layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)

        return layout
    }

    var onCreateDivider: ((Int) -> View) = {
        val divider = View(context)
        divider.setBackgroundColor(rowDividerColor)
        divider.layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, rowDividerHeight)
        divider
    }

    /**
     * Implementation of the TitleView visual aspect
     */
    var customizeTitleView: ((Section, TextView) -> Unit) = { row, textView ->

    }

    /**
     * Use this lambda to change the visual aspect of the TitleView
     */
    protected fun createSectionTitleView(): TextView {

        val titleView = AppCompatTextView(context)
        titleView.text = title
        val layoutParams = ViewGroup.MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        titleView.layoutParams = layoutParams

        //margin not working here
        titleView.setPadding(
                DimensionUtils.dpToPx(TITLE_DEFAULT_MARGIN_LEFT),
                DimensionUtils.dpToPx(TITLE_DEFAULT_MARGIN_TOP),
                DimensionUtils.dpToPx(TITLE_DEFAULT_MARGIN_BOTTOM),
                DimensionUtils.dpToPx(TITLE_DEFAULT_MARGIN_RIGHT))

        titleView.layoutParams = layoutParams
        titleView.setTextColor(titleTextColor)
        titleView.text = this.title
        titleView.textSize = 16f

        customizeTitleView.invoke(this, titleView)

        return titleView
    }

}