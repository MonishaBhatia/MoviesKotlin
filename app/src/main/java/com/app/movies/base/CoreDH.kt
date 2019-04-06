package com.app.movies.base

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.app.movies.BuildConfig
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CoreDH {

  private lateinit var okhttpClient: OkHttpClient
  lateinit var retrofit: Retrofit
  lateinit var gson: Gson
  private lateinit var sharedPrefs: SharedPreferences
  lateinit var scheduler: Scheduler

  fun init(context: Context) {

    //Network
    val rxConverter = providesRxJavaCallAdapterFactory()
    val gsonFactory = providesGsonConverterFactory()
    val cache = providesOkhttpCache(context)
    okhttpClient = providesOkHttpClient(cache)
    retrofit = providesRetrofit(
        gsonFactory, rxConverter, okhttpClient
    )
    gson = Gson()

    //Threading
    scheduler = AppScheduler()

    //Storage
    sharedPrefs =
      providesSharedPreferences(context)
  }

  private fun providesSharedPreferences(context: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
  }

  private fun providesRetrofit(
    gsonConverterFactory: GsonConverterFactory,
    rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
    okHttpClient: OkHttpClient
  ): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE + "/")
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .client(okHttpClient)
        .build()
  }

  private fun providesOkHttpClient(cache: Cache): OkHttpClient {
    val client = OkHttpClient.Builder()
        .cache(cache)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
      val logging = HttpLoggingInterceptor()
      logging.level = Level.BODY
      client.addInterceptor(logging)
    }

    return client.build()
  }

  private fun providesOkhttpCache(context: Context): Cache {
    val cacheSize = 10 * 1024 * 1024 // 10 MB
    return Cache(context.cacheDir, cacheSize.toLong())
  }

  private fun providesGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
  }

  private fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
    return RxJava2CallAdapterFactory.create()
  }

}