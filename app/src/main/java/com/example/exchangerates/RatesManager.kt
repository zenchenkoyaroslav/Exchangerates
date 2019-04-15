package com.example.exchangerates

import rx.Observable


class RatesManager(private val api: RestApi = RestApi()) {

    fun getRates(date: String): Observable<List<RatesItem>> {
        return Observable.create {
                subscriber ->

            val callResponse = api.getRates(date)
            val response = callResponse.execute()

            if (response.isSuccessful){
                val rates = response.body().Currency?.map {
                    val item = it
                    RatesItem(item.CharCode, item.Scale, item.Name, item.Rate, null)
                }
                subscriber.onNext(rates)
                subscriber.onCompleted()
            }else{
                subscriber.onError(Throwable(response.message()))
            }

        }
    }
}