package com.lbw.jphoto.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lbw.jphoto.R


/**
 * toast 类
 */
object ToastUtil {

    private var mToast: Toast? = null

    fun show(context: Context, info: String) {
        if (mToast != null) {
            mToast!!.setText(info)
        } else {
            mToast = Toast.makeText(context, info, Toast.LENGTH_SHORT)
        }

        mToast!!.show()
    }

    fun showSnackBar(context: Context, view: View, text: String, duration: Int = Snackbar.LENGTH_SHORT) {
        val mSnackBar: Snackbar = Snackbar.make(view, text, duration)
        mSnackBar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        mSnackBar.show()
    }

}
