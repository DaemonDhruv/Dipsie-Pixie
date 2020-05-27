package com.dhruvbindoria.dipsiepixie

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.AppCompatRadioButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnFocusChangeListener {

    var mDpi: Int = 480
    var mDp: Float = 1F
    var mPx: Float = 1F
    var isDpSelected: Boolean = false
    var isPxSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //To make the status bar transparent
        //Also, in styles.xml set: <item name="android:windowDrawsSystemBarBackgrounds">true</item>
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.parseColor("#1AF9F9F9")

        isDpSelected = true

        edt_dp.setText(mDp.toString())
        edt_dpi.setText(mDpi.toString())

        edt_dp.onFocusChangeListener = this
        edt_px.onFocusChangeListener = this
        edt_dpi.onFocusChangeListener = this
    }

    fun setDpi(view: View) {
        mDpi = (view as AppCompatRadioButton).text.toString().toInt()
        edt_dpi.setText(mDpi.toString())
    }


    override fun onFocusChange(view: View, hasFocus: Boolean) {
        if (!hasFocus) {
            when (view.id) {
                R.id.edt_dp -> {
                    isDpSelected = true
                    isPxSelected = false
                    mDp = edt_dp.text.toString().toFloat()
                }
                R.id.edt_px -> {
                    isPxSelected = true
                    isDpSelected = false
                    mPx = edt_px.text.toString().toFloat()
                }
                R.id.edt_dpi -> {
                    val dpi: Int = edt_dpi.text.toString().toInt()
                    if (dpi > 0) {
                        constraintRadioGroup.clearCheck()
                        mDpi = edt_dpi.text.toString().toInt()
                        //Try highlighting the button corresponding to the dpi if it matches any of the button.
                    }
                }
            }
        }
    }


    fun calculate(view: View) {
        if (this.isDpSelected) {
            val px: Float = mDp * mDpi / 160
            edt_px.setText(px.toString())
        }
        if (isPxSelected) {
            val dp: Float = mPx / mDpi * 160
            edt_dp.setText(dp.toString())
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            var v: View? = currentFocus
            if (v is EditText) {
                var outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains((event.rawX).toInt(), (event.rawY).toInt())) {
                    v.clearFocus();
                    var imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

}






