package com.example.exchangerates.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NBRBApi {

    @GET("/Services/XmlExRates")
    fun getRates(@Query("ondate") date: String)
            : Call<NBRBDailyExRatesResponse>

}