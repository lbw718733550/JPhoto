package com.lbw.jphoto.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lbw.jphoto.R
import com.lbw.jphoto.ui.view.MainView
import com.lzx.nickphoto.bean.PhotoInfo
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() , MainView{
    override fun setContentViewId(): Int {
        return R.layout.activity_main
    }


    override fun init() {

    }

    override fun OnGetPhotoSuccese(result: List<PhotoInfo>) {
    }

    override fun showPro(isShow: Boolean) {
        if(isShow) load_pro.visibility = View.VISIBLE else load_pro.visibility = View.GONE
    }


}
