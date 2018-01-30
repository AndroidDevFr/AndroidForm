package com.github.androiddevfr.form.rows

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.androiddevfr.form.core.DimensionUtils
import com.github.androiddevfr.form.core.ResultHandler
import java.text.SimpleDateFormat
import java.util.*

open class DateRow(context: Context) : AbstractTextRow<Date>(context) {

    //will be created by onCreateView
    var dateView: TextView? = null

    var dateFormat = "dd/MM/yyyy"
        set(value) {
            field = value
            dateFormatter.applyPattern(value)
        }

    val dateFormatter = SimpleDateFormat(dateFormat, Locale.getDefault())

    var value: Date? = null
        set(value) {
            field = value
            dateView?.text = dateFormatter.format(value)
        }
    var defaultDate: Date? = null

    protected var datePicker: ((DateRow, ResultHandler<Date>) -> Unit) = { dateRow, resultHandler ->
        openDefaultDatePicker(this, resultHandler)
    }

    /**
     * Change this lambda to have a custom Date Picker (on click)
     */
    fun datePicker(block: (DateRow, ResultHandler<Date>) -> Unit) {
        this.datePicker = block
    }

    protected var customizeDateView: ((DateRow, TextView) -> Unit) = { dateRow, textView ->

    }

    /**
     * Change this lambda to have a custom Date View
     */
    fun customizeDateView(block: (DateRow, TextView) -> Unit) {
        this.customizeDateView = block
    }

    init {
        validator = { v -> v != null }
        onCreateView<DateRow> {
            val layout = RelativeLayout(context)

            //Generated the DateView
            createDateView()
            val editTextLayoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            editTextLayoutParams.leftMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_LEFT)
            editTextLayoutParams.topMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_TOP)
            editTextLayoutParams.bottomMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_BOTTOM)
            editTextLayoutParams.rightMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_RIGHT)
            editTextLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            editTextLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            dateView?.layoutParams = editTextLayoutParams
            dateView?.text = dateFormatter.format(defaultDate)
            layout.addView(dateView)

            //Generated the Title
            createTitleView()
            val titleLayoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            titleLayoutParams.leftMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_LEFT)
            titleLayoutParams.topMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_TOP)
            titleLayoutParams.bottomMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_BOTTOM)
            titleLayoutParams.rightMargin = DimensionUtils.dpToPx(DEFAULT_MARGIN_RIGHT)
            titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            titleView?.layoutParams = titleLayoutParams
            layout.addView(titleView)

            layout
        }
        addOnViewCreatedListener<DateRow> {
            dateView?.setOnClickListener {
                onValueClicked()
            }
        }
    }

    /**
     * Use this lambda to change the visual aspect of the TitleView
     */
    protected fun createDateView(): TextView {
        dateView = AppCompatTextView(context)
        customizeDateView.invoke(this, this.dateView as TextView)
        return dateView as TextView
    }

    override fun onValueClicked() {
        val resultHandler = ResultHandler<Date>()
        resultHandler.registerListener {
            value = it
        }
        datePicker.invoke(this, resultHandler)
    }

    protected fun openDefaultDatePicker(row: DateRow, resultHandler: ResultHandler<Date>) {
        val defaultDate = Calendar.getInstance()
        if (row.defaultDate != null) {
            defaultDate.time = row.defaultDate
        }
        val dialog = DatePickerDialog(row.context,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(Calendar.YEAR, year)
                    newDate.set(Calendar.MONTH, month)
                    newDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    resultHandler.onValue(newDate.time)
                },
                defaultDate.get(Calendar.YEAR),
                defaultDate.get(Calendar.MONTH),
                defaultDate.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }

    override fun value(): Date? {
        return value
    }
}