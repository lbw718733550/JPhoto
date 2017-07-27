package com.lbw.jphoto.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.lbw.jphoto.Constant
import com.lbw.jphoto.MyApplication

import java.io.File
import java.lang.reflect.Field
import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by lin on 16/12/21.
 */
class ServiceFactory {

    private val mGsonDateFormat: Gson

    init {
        mGsonDateFormat = GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create()

    }

    private object SingletonHolder {
        val INSTANCE = ServiceFactory()
    }

    companion object{
        val instance : ServiceFactory by lazy { SingletonHolder.INSTANCE }
        private val DEFAULT_TIMEOUT: Long = 10
    }

    /**
     * create a service

     * @param serviceClass
     * *
     * @param <S>
     * *
     * @return
    </S> */
    fun <S> createService(serviceClass: Class<S>): S {
//        var baseUrl = ""
//        try {
//            val field1 = serviceClass.getField("BASE_URL")
//            baseUrl = field1.get(serviceClass) as String
//        } catch (e: NoSuchFieldException) {
//            e.printStackTrace()
//        } catch (e: IllegalAccessException) {
//            e.message
//            e.printStackTrace()
//        }

        val retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(mGsonDateFormat))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(serviceClass)
    }
    //定制OkHttp
    //设置超时时间
    //设置缓存
    private val okHttpClient: OkHttpClient
        get() {
            val httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            val httpCacheDirectory = File(MyApplication.instance.getCacheDir(), "OkHttpCache")
            httpClientBuilder.cache(Cache(httpCacheDirectory, (10 * 1024 * 1024).toLong()))
            return httpClientBuilder.build()
        }

}
