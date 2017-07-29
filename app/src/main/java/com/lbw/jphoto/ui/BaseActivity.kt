package com.lbw.jphoto.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import java.io.Serializable

/**
 * Created by del on 17/7/26.
 */
abstract class BaseActivity : AppCompatActivity() {

    lateinit var instance:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (setContentViewId() <= 0) {
            throw RuntimeException("layout resId undefind")
        }
        setContentView(setContentViewId())
        instance = this
        init()
    }

    abstract fun init()

    abstract fun setContentViewId() :Int



    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 返回键
            baseFinish()
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            return false
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            return false
        }
        return false
    }

    fun baseStartActivity(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        this.startActivity(intent)
    }


    fun baseStartActivity(context: Context, cls: Class<*>, key: Array<String>,
                          value: Array<String>) {
        val intent = Intent(context, cls)
        for (j in key.indices) {
            intent.putExtra(key[j], value[j])
        }
        this.startActivity(intent)
    }

    private val KEY = "OBJ"
    fun baseStartActivity(context: Context, cls: Class<*>,value: Serializable) {
        val intent = Intent(context, cls)
        val bundle = Bundle()
        bundle.putSerializable(KEY, value)
        intent.putExtra(KEY, bundle)
        this.startActivity(intent)
    }

    fun baseGetIntent(key: String): String {
        val intent = intent
        return intent.getStringExtra(key)
    }


    fun baseGetIntent(): Serializable? {
        val intent = intent
        if (null == intent.getBundleExtra(KEY)) {
            return null
        }
        return intent.getBundleExtra(KEY).getSerializable(KEY)
    }


    fun baseFinish(){
        finish()
    }
}