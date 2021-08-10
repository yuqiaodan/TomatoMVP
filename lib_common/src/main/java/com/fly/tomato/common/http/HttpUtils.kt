package com.fly.tomato.common.http

import com.fly.tomato.common.BuildConfig
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by qiaodan on 2021/8/10
 * Description:
 */
object HttpUtils {

    val TAG = "HTTP-LOG"

    //只是一个例子 需要添加其他client的直接在外部重新新建retrofit对象
    fun getBaseRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder().client(getBaseHttpInterceptor().build())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    //添加基础HTTP拦截器 目前只默认添加了一个网络请求日志 以及超时时间10秒
    fun getBaseHttpInterceptor(): OkHttpClient.Builder {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(getHttpLogInterceptor())
        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(10, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(10, TimeUnit.SECONDS)
        return httpClientBuilder
    }

    fun getHttpLogInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .setLevel(if (BuildConfig.IS_DEBUG) Level.BASIC else Level.NONE)
            .log(Platform.INFO)
            .request(TAG)
            .response(TAG)
            .build()
    }


}