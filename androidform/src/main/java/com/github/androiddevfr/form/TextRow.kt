package com.github.androiddevfr.form

import android.app.DatePickerDialog
import android.content.Context
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import java.util.*

open abstract class AbstractTextRow<V>(context: Context) : Row<V>(context) {
    var title: String? = null
    var placeholder: String? = null

    //override to have a custom behavior on value click (eg: open dialog)
    open fun onValueClicked(){}
}

open class TextRow(context: Context) : AbstractTextRow<String>(context) {

    lateinit var edit: EditText

    override fun value(): String? {
        return edit.text.toString()
    }

    init {
        validator = { v -> v != null && v.isNotEmpty() }
    }

    override fun onCreateView() : View {
        return RelativeLayout(context) //TODO create view
    }

}

open class PhoneRow(context: Context) : TextRow(context) {

    init {
        validator = { v -> Patterns.PHONE.matcher(v).matches() }
    }

    override fun onCreateView() : View {
        return RelativeLayout(context) //TODO create view
    }

}

open class DateRow(context: Context) : AbstractTextRow<Date>(context) {

    init {
        validator = { v -> v != null }
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

    override fun onCreateView() : View {
        return RelativeLayout(context) //TODO create view
    }
}