package com.github.androiddevfr.form

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import com.github.androiddevfr.form.core.DimensionUtils
import com.github.androiddevfr.form.rows.AbstractRow
import com.github.androiddevfr.form.rows.Row
import com.github.androiddevfr.form.section.Section

class Form : LinearLayout {

    val sections = mutableListOf<Section>()
    val formCreator = FormCreator(this)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        orientation = VERTICAL
    }

    fun create(block: (FormCreator.() -> Unit)): FormCreator {
        block.invoke(formCreator)
        build()
        return formCreator
    }

    /**
     * Construct the form's view
     */
    private fun build() {
        removeAllViews()

        for (index in 0 until sections.size) {
            if (index > 0) {
                addView(formCreator.onCreateSectionDivider.invoke(index))
            }
            val sectionView = sections[index].onCreateView()
            addView(sectionView, sectionView.layoutParams)
        }
    }

    fun values(): Map<Int, Any?> {
        val map = mutableMapOf<Int, Any?>()
        sections.forEach { section: Section ->
            section.rows.forEach { row: Row<*> ->
                if (row is AbstractRow<*>) {
                    map.put(row.id, row.value())
                }
            }
        }
        return map;
    }

    fun rowById(id: Int): Row<*>? {
        sections.forEach { section: Section ->
            section.rows.forEach { row: Row<*> ->
                if (row.id == id) {
                    return row
                }
            }
        }
        return null
    }
}

class FormCreator(private val form: Form) {

    var sectionDividerHeight = DimensionUtils.dpToPx(16f)
    var sectionDividerColor = Color.TRANSPARENT

    fun section(title: String, block: Section.() -> Unit): FormCreator {
        val section = Section(form.context, title)
        form.sections.add(section)
        block.invoke(section)
        return this
    }

    /**
     * Set lambda to have a custom section divider
     */
    var onCreateSectionDivider: ((Int) -> View) = {
        val divider = View(form.context)
        divider.setBackgroundColor(sectionDividerColor)
        divider.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, sectionDividerHeight)
        divider
    }

}

