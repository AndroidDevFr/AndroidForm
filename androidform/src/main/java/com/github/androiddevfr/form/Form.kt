package com.github.androiddevfr.form

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import com.github.androiddevfr.form.core.DimensionUtils
import com.github.androiddevfr.form.rows.AbstractTitleRow
import com.github.androiddevfr.form.rows.Row
import com.github.androiddevfr.form.section.Section

class Form : NestedScrollView {

    companion object {
        val SECTION_MARGIN_LEFT = 8
        val SECTION_MARGIN_RIGHT = 8
        val SECTION_MARGIN_TOP = 8
        val SECTION_MARGIN_BOTTOM = 8
    }

    val sections = mutableListOf<Section>()
    val formCreator = FormCreator(this)

    val layout: ViewGroup;

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
        }
        addView(layout)
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
        layout.removeAllViews()

        sections.forEach { section ->
            layout.addView(
                    section.onCreateView(),
                    LinearLayout.LayoutParams(MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                        setMargins(
                                DimensionUtils.dpToPx(SECTION_MARGIN_LEFT),
                                DimensionUtils.dpToPx(SECTION_MARGIN_TOP),
                                DimensionUtils.dpToPx(SECTION_MARGIN_RIGHT),
                                DimensionUtils.dpToPx(SECTION_MARGIN_BOTTOM)
                        )
                    })
        }
    }

    fun values(): Map<Int, Any?> {
        val map = mutableMapOf<Int, Any?>()
        sections.forEach { section: Section ->
            section.rows.forEach { row: Row<*> ->
                if (row is AbstractTitleRow<*>) {
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

    fun section(title: String, block: Section.() -> Unit): FormCreator {
        Section(form.context, title).apply {
            form.sections.add(this)
            block.invoke(this)
        }
        return this;
    }

}

