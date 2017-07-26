package com.lbw.jphoto

import android.app.Application
import kotlin.properties.Delegates

/**
 * Created by del on 17/7/26.
 */
class MyApplication :Application(){

    companion object{
        //kotlin中使用var修饰的属性不允许不初始化，但是有的时候需要在使用的时候进行初始化，
        // 针对该情况，可以使用Delegates.notNull()代理，运行时对属性进行判断，若未初始化，则抛出java.lang.IllegalStateException异常：
        var instance : MyApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

    }
}