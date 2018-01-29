package com.github.androiddevfr.form

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import com.github.androiddevfr.form.rows.AbstractTextRow
import com.github.androiddevfr.form.rows.Row
import com.github.androiddevfr.form.section.Section

class Form : LinearLayout {

    val sections = mutableListOf<Section>()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        orientation = VERTICAL
    }

    fun create(block: (FormCreator.() -> Unit)): FormCreator {
        val formCreator = FormCreator(this)
        block.invoke(formCreator);
        build()
        return formCreator
    }

    private fun build() {
        sections.forEach{ section ->
            val sectionView = section.onCreateView()
            addView(sectionView, MATCH_PARENT, WRAP_CONTENT)
        }
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

