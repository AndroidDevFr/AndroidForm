package com.github.androiddevfr.form.kotlin.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.androiddevfr.form.DateRow
import com.github.androiddevfr.form.Form
import java.util.*

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
                }
                phoneRow {
                    id = 3
                    title = "Phone Row"
                    placeholder = "020202020202"

                }
            }
            section("Section2") {
                id = 1
                dateRow {
                    id = 5
                    title = "Text Row"
                    value = Date()
                }
            }
        }

        //all values
        var values = form.values()

        //single value
        val row = form.rowById(5) as DateRow?
        val value = row?.value
    }
}
