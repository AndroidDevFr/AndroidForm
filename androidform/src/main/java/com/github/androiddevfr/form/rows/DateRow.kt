package com.github.androiddevfr.form.rows

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.github.androiddevfr.form.core.ResultHandler
import java.util.*

open class DateRow(context: Context) : AbstractTextRow<Date>(context) {

    //will be created by onCreateView
    var dateView: View? = null

    init {
        validator = { v -> v != null }
        onCreateView<DateRow> {
            RelativeLayout(context) //TODO create view
        }
    }

    var value: Date? = null
    var defaultDate: Date? = null

    var datePicker: ((DateRow) -> ResultHandler<Date>) = {
        openDefaultDatePicker(this)
    }

    override fun onValueClicked() {
        datePicker.invoke(this).registerListener{
            value = it
        }
    }

    private fun openDefaultDatePicker(row: DateRow): ResultHandler<Date> {
        val resultHandler = ResultHandler<Date>()

        val defaultDate = Calendar.getInstance()
        if (row.defaultDate != null) {
            defaultDate.time = row.defaultDate
        }
        val dialog = DatePickerDialog(row.context,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
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

        return resultHandler
    }

    override fun value(): Date? {
        return value
    }
}