package com.github.androiddevfr.form.rows

import android.content.Context
import android.support.v7.widget.AppCompatSeekBar
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.github.androiddevfr.form.core.DimensionUtils

open class SeekBarRow(context: Context) : AbstractTitleRow<Int>(context) {

    companion object {
        const val SEEK_BAR_MIN_WIDTH_DP = 128
    }

    var maxValue: Int = 100

    var value: Int = 0
        set(value) {
            field = value
            seekBarView?.progress = value
        }

    var seekBarView: AppCompatSeekBar? = null


    protected var customizeSeekBar: ((SeekBarRow, SeekBar) -> Unit) = { seekBarRow, seekBar ->

    }

    init {
        validator = { true }
        onCreateView<SeekBarRow> {
            val layout = RelativeLayout(context)

            //Generated the Slider
            createSeekBar()
            val seekBarLayoutParams = RelativeLayout.LayoutParams(DimensionUtils.dpToPx(SEEK_BAR_MIN_WIDTH_DP), ViewGroup.LayoutParams.WRAP_CONTENT)
            seekBarLayoutParams.leftMargin = DimensionUtils.dpToPx(AbstractTitleRow.DEFAULT_MARGIN_LEFT)
            seekBarLayoutParams.topMargin = DimensionUtils.dpToPx(AbstractTitleRow.DEFAULT_MARGIN_TOP)
            seekBarLayoutParams.bottomMargin = DimensionUtils.dpToPx(AbstractTitleRow.DEFAULT_MARGIN_BOTTOM)
            seekBarLayoutParams.rightMargin = DimensionUtils.dpToPx(AbstractTitleRow.DEFAULT_MARGIN_RIGHT)
            seekBarLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            seekBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            seekBarView?.layoutParams = seekBarLayoutParams
            layout.addView(seekBarView)

            //Generated the Title
            createTitleView(TITLE_VIEW_ID)
            val titleLayoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            titleLayoutParams.leftMargin = DimensionUtils.dpToPx(AbstractTitleRow.DEFAULT_MARGIN_LEFT)
            titleLayoutParams.topMargin = DimensionUtils.dpToPx(AbstractTitleRow.DEFAULT_MARGIN_TOP)
            titleLayoutParams.bottomMargin = DimensionUtils.dpToPx(AbstractTitleRow.DEFAULT_MARGIN_BOTTOM)
            titleLayoutParams.rightMargin = DimensionUtils.dpToPx(AbstractTitleRow.DEFAULT_MARGIN_RIGHT)
            titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            titleView?.layoutParams = titleLayoutParams
            layout.addView(titleView)

            layout
        }
    }

    override fun value(): Int {
        return value
    }

    /**
     * Change this lambda to have a custom Date View
     */
    fun customizeSeekBar(block: (SeekBarRow, SeekBar) -> Unit) {
        this.customizeSeekBar = block
    }

    /**
     * Use this lambda to change the visual aspect of the SliderView
     */
    protected fun createSeekBar(): SeekBar {
        seekBarView = AppCompatSeekBar(context)
        customizeSeekBar.invoke(this, this.seekBarView as SeekBar)

        seekBarView?.max = maxValue
        seekBarView?.progress = value

        seekBarView?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                value = progress
                onValueChange()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
        return seekBarView as SeekBar
    }
}