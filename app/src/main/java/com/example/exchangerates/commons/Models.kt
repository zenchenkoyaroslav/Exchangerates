package com.example.exchangerates.commons

import com.example.exchangerates.commons.adapter.AdapterConstants
import com.example.exchangerates.commons.adapter.ViewType
import java.util.*

data class RatesItem(
    val CharCode: String?,
    val Scale: String?,
    val Name: String?,
    val RateToday: String?,
    val RateTomorrow: String?
) : ViewType {
    override fun getViewType() = AdapterConstants.RATES
}

data class DateItem(
    val todayDate: Calendar,
    val tomorrowDate: Calendar
): ViewType {
    override fun getViewType() = AdapterConstants.DATE

}