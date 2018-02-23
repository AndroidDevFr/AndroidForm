package com.github.androiddevfr.form.kotlin.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.androiddevfr.form.Form
import com.github.androiddevfr.form.rows.DateRow
import com.github.androiddevfr.form.rows.TextRow
import kotlinx.android.synthetic.main.activity_main.getValues
import java.util.Date

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //can use kotlin extension, just to be clear it's a View
        val form = findViewById<Form>(R.id.formContainer)

        form.create {
            section("Section1") {
                id = 0
                textRow {
                    id = 2
                    title = "Text Row"

                    placeholder = "Enter text here"

                    validator = { s ->
                        s != null && s.length > 3
                    }

                    onValueChanged<TextRow> { textRow, value ->
                        //change background following value, for example
                        //textRow.view?.background = ...
                    }
                }
                phoneRow {
                    id = 3
                    title = "Phone Row"
                    placeholder = "020202020202"
                }
                emailRow {
                    id = 3
                    title = "Email Row"
                    placeholder = "android@gmail.com"
                }
            }
            section("Section2") {
                id = 1
                dateRow {
                    id = 5
                    title = "Date Row"
                    defaultDate = Date()
                    value = Date()
                }

                singleChoice {
                    id = 6
                    title = "Single Choice"
                    items = listOf("Item 0", "Item 1", "Item 2")
                }

                multiChoice {
                    id = 6
                    title = "Multi Choice"
                    items = listOf("Item 0", "Item 1", "Item 2")
                }

            }
            section("Section 3") {
                id = 6
                seekBarRow {
                    id = 7
                    title = "Slider row"
                    maxValue = 42
                    value = 33
                }
            }
        }

        getValues.setOnClickListener {
            //all values
            val values = form.values()

            for ((key, value) in values.entries) {
                Log.i("values", "$key → $value")
            }
        }


        //single value
        val row = form.rowById(5) as DateRow?
        val value = row?.value
    }
}
