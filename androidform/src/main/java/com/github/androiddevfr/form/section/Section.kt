package com.github.androiddevfr.form.section

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.CardView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import com.github.androiddevfr.form.core.DimensionUtils
import com.github.androiddevfr.form.core.DimensionUtils.dpToPx
import com.github.androiddevfr.form.rows.*

class Section(private val context: Context, var title: String) {

    companion object {
        val SECTION_PADDING_LEFT = 8
        val SECTION_PADDING_RIGHT = 8
        val SECTION_PADDING_TOP = 8
        val SECTION_PADDING_BOTTOM = 8

        val TITLE_DEFAULT_MARGIN_TOP = 6
        val TITLE_DEFAULT_MARGIN_BOTTOM = 6
        val TITLE_DEFAULT_MARGIN_LEFT = 16
        val TITLE_DEFAULT_MARGIN_RIGHT = 16
    }

    var id = -1
    var titleTextColor = Color.parseColor("#424FB5")
    var rowDividerColor = Color.TRANSPARENT
    var rowDividerHeight = dpToPx(1)

    var titleView: View? = null

    val rows = mutableListOf<Row<*>>()

    /**
     * Add a row with title/placeholder and an EditText
     *
     * ----------------------------------------
     * |        TITLE                         |
     * | (icon)                     EDITTEXT  |
     * |        PLACEHOLDER                   |
     * ----------------------------------------
     */
    fun textRow(block: (TextRow.() -> Unit)): Section {
        return row(TextRow(context), block)
    }

    /**
     * Add a row with title/placeholder and an EditText(phone)
     *
     * ----------------------------------------
     * |        TITLE                         |
     * | (icon)                       PHONE   |
     * |        PLACEHOLDER                   |
     * ----------------------------------------
     */
    fun phoneRow(block: (PhoneRow.() -> Unit)): Section {
        return row(PhoneRow(context), block)
    }

    /**
     * Add a row with title/placeholder and an EditText(email)
     *
     * ----------------------------------------
     * |        TITLE                         |
     * | (icon)                        PHONE  |
     * |        PLACEHOLDER                   |
     * ----------------------------------------
     */
    fun emailRow(block: (EmailRow.() -> Unit)): Section {
        return row(EmailRow(context), block)
    }

    /**
     * Add a row with title/placeholder and an DatePicker)
     *
     * ----------------------------------------
     * |        TITLE                         |
     * | (icon)                         DATE  | -> Open Date Picker
     * |        PLACEHOLDER                   |
     * ----------------------------------------
     */
    fun dateRow(block: (DateRow.() -> Unit)): Section {
        return row(DateRow(context), block)
    }

    /**
     * Add a row with title/placeholder and a SeekBar
     *
     * ----------------------------------------
     * |        TITLE                         |
     * | (icon)                    -----O--   |
     * |        PLACEHOLDER                   |
     * ----------------------------------------
     */
    fun seekBarRow(block: (SeekBarRow.() -> Unit)): Section {
        return row(SeekBarRow(context), block)
    }

    /**
     * Add a row with title/placeholder and a Spinner
     *
     * ----------------------------------------
     * | TITLE                                |
     * |                                      |
     * |        -----------------------       |
     * | (icon) | default           v |       |
     * |        -----------------------       |
     * ----------------------------------------
     */
    fun selection(block: (SelectionRow.() -> Unit)): Section {
        return row(SelectionRow(context), block)
    }

    /**
     * Add a multi choice row
     * TODO schema
     */
    fun multiChoice(block: (MultiChoiceRow.() -> Unit)): Section {
        return row(MultiChoiceRow(context), block)
    }

    /**
     * Add a single choice row
     * TODO schema
     */
    fun singleChoice(block: (SingleChoiceRow.() -> Unit)): Section {
        return row(SingleChoiceRow(context), block)
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
        val cardView = CardView(context).apply {
            setContentPadding(
                    DimensionUtils.dpToPx(SECTION_PADDING_LEFT),
                    DimensionUtils.dpToPx(SECTION_PADDING_TOP),
                    DimensionUtils.dpToPx(SECTION_PADDING_RIGHT),
                    DimensionUtils.dpToPx(SECTION_PADDING_BOTTOM)
            )
        }

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
            cardView.addView(this)
        }

        titleView = createSectionTitleView().apply {
            layout.addView(this, LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                setMargins(
                        DimensionUtils.dpToPx(TITLE_DEFAULT_MARGIN_LEFT),
                        DimensionUtils.dpToPx(TITLE_DEFAULT_MARGIN_TOP),
                        DimensionUtils.dpToPx(TITLE_DEFAULT_MARGIN_RIGHT),
                        DimensionUtils.dpToPx(TITLE_DEFAULT_MARGIN_BOTTOM)
                )
            })
        }


        for (index in 0 until rows.size) {
            val row = rows[index]
            row.create()
            layout.addView(row.view)
            if (index != rows.size - 1) {
                layout.addView(onCreateDivider(index))
            }
        }

        return cardView
    }

    var onCreateDivider: ((Int) -> View) = {
        View(context).apply {
            setBackgroundColor(rowDividerColor)
            layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, rowDividerHeight)
        }
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
        return AppCompatTextView(context).apply {
            text = title
            setTextColor(titleTextColor)
            typeface = Typeface.DEFAULT_BOLD
            textSize = 14f


            customizeTitleView.invoke(this@Section, this);
        }
    }

}