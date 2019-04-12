package com.example.exchangerates

import rx.Observable


class RatesManager {

    fun getRates(): Observable<List<RatesItem>> {
        return Observable.create {
                subscriber ->

            val news = mutableListOf<RatesItem>()
            for (i in 1..10) {
                news.add(RatesItem(
                    "USD",
                    "1",
                    "Доллар США",
                    "1,9452",
                    "1,9444"
                ))
            }
            subscriber.onNext(news)
            subscriber.onCompleted()
        }
    }
}