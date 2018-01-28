package com.github.androiddevfr.form

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