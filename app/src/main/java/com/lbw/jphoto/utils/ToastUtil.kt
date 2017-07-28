package com.lbw.jphoto.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast


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



}
