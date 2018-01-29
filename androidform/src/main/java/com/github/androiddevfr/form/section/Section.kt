package com.github.androiddevfr.form.section

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.github.androiddevfr.form.rows.DateRow
import com.github.androiddevfr.form.rows.PhoneRow
import com.github.androiddevfr.form.rows.Row
import com.github.androiddevfr.form.rows.TextRow

class Section(private val context: Context, var title: String) {
    var id = -1

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
    fun textRow(block: (TextRow.() -> Unit)) : Section {
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
    fun phoneRow(block: (PhoneRow.() -> Unit)) : Section {
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
    fun dateRow(block: (DateRow.() -> Unit)) : Section {
        return row(DateRow(context), block)
    }

    fun <R : Row<*>> row(row: R, block: (R.() -> Unit)) : Section {
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
        rows.forEach{ row ->
            row.create()
            layout.addView(row.view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        return layout
    }

}