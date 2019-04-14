package com.example.exchangerates

import rx.Observable
import java.util.*

class DateManager {
    fun getDate(): Observable<DateItem> {
        return Observable.create {
                subscriber ->

            val today = Calendar.getInstance()
            val tomorrow = Calendar.getInstance()
            tomorrow.add(Calendar.DAY_OF_MONTH, 1)

            val date = DateItem(today, tomorrow)

            subscriber.onNext(date)
            subscriber.onCompleted()
        }
    }
}