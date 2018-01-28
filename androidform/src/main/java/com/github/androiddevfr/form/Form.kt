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

    fun create(block: (FormCreator.() -> Unit)): FormCreator {
        val formCreator = FormCreator(this)
        block.invoke(formCreator);
        build()
        return formCreator
    }

    fun build() {
        //TODO build the view
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
        val section = Section(form.context, title)
        form.sections.add(section)
        block.invoke(section)
        return this
    }

}

