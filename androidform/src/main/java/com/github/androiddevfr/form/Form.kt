package com.github.androiddevfr.form

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import java.util.*

class Form : LinearLayout {

    val sections = mutableListOf<Section>()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun create(): FormCreator {
        return FormCreator(this)
    }

    fun create(block: (FormCreator.() -> Unit)): FormCreator {
        val formCreator = FormCreator(this)
        block.invoke(formCreator);
        build()
        return formCreator
    }

    fun build() : View {
        return View(context) //TODO
    }

    fun values() : Map<Int, Any?> {
        val map = mutableMapOf<Int, Any?>()
        sections.forEach{ section: Section ->
            section.rows.forEach { row: Row<*> ->
                if(row is AbstractTextRow<*>){
                    map.put(row.id, row.value())
                }
            }
        }
        return map;
    }

    fun rowById(id: Int): Row<*>? {
        sections.forEach { section: Section ->
            section.rows.forEach { row: Row<*> ->
                if(row.id == id){
                    return row
                }
            }
        }
        return null
    }
}

class FormCreator(private val form: Form) {

    fun section(title: String, block: Section.() -> Unit): FormCreator {
        val section = Section(title)
        form.sections.add(section)
        block.invoke(section)
        return this
    }

}

class Section(var title: String) {
    var id = -1;
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
        return row(TextRow(), block)
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
        return row(PhoneRow(), block)
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
        return row(DateRow(), block)
    }

    fun <R : Row<*>> row(row: R, block: (R.() -> Unit)) : Section {
        rows.add(row)
        block.invoke(row)
        return this
    }

    fun id(id: Int): Section {
        this.id = id;
        return this;
    }

}

abstract class Row<V> {
    var id: Int = -1

    abstract fun value() : V?
    abstract fun validate() : Boolean
}

open abstract class AbstractTextRow<V> : Row<V>() {
    var title: String? = null
    var placeholder: String? = null
}

open class TextRow : AbstractTextRow<String>() {

    lateinit var edit: EditText

    override fun value(): String? {
        return edit.text.toString()
    }

    override fun validate() : Boolean {
        return edit.text.isNotEmpty()
    }

}

open class PhoneRow : TextRow(){

    override fun validate() : Boolean {
        return true; //TODO : Validate
    }

}

open class DateRow : AbstractTextRow<Date>(){
    override fun validate(): Boolean {
        return value != null;
    }

    var value: Date? = null

    private var clickAction: ((DateRow) -> Date)? = null

    fun customizePickDate(callback: ((DateRow) -> Date)): DateRow {
        this.clickAction = callback
        return this
    }

    override fun value(): Date? {
        return value
    }
}

