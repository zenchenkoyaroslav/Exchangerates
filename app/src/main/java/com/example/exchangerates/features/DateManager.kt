package com.example.exchangerates.features

import com.example.exchangerates.commons.DateItem
import java.util.*

class DateManager {
    fun getDate(calendar: Calendar) : DateItem {

        val tomorrow = (calendar.clone() as Calendar).also {
            it.add(Calendar.DAY_OF_MONTH, 1)
        }

        return DateItem(calendar, tomorrow)

    }
}