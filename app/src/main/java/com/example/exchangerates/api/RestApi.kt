package com.example.exchangerates.api

import okhttp3.OkHttpClient
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import rx.schedulers.Schedulers

class RestApi {
    private val nbrbApi: NBRBApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.nbrb.by/")
            .client(OkHttpClient())
            .addConverterFactory(
                SimpleXmlConverterFactory.createNonStrict(
                    Persister(AnnotationStrategy())
                )
            )
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        nbrbApi = retrofit.create<NBRBApi>(NBRBApi::class.java)
    }

    fun getRates(date: String): Call<NBRBDailyExRatesResponse> {
        return nbrbApi.getRates(date)
    }
}