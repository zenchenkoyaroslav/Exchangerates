package com.example.exchangerates

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.rates_fragment.*
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class RatesFragment : RxBaseFragment() {

    private val ratesManager by lazy { RatesManager() }
    private val dateManager by lazy { DateManager() }

    private var format = SimpleDateFormat("MM.dd.YYYY")
    private var date = DateItem(Calendar.getInstance(), Calendar.getInstance())
    private var todayRatesItems = mutableListOf<RatesItem>()
    private var tomorrowRatesItems = mutableListOf<RatesItem>()
    private var ratesItems = mutableListOf<RatesItem>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.rates_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rates_list.setHasFixedSize(true)
        rates_list.layoutManager = LinearLayoutManager(context)
        initAdapter()

        if (savedInstanceState == null) {
            requestDates()
            requestRates(format.format(date.todayDate.time), todayRatesItems)
            requestRates(format.format(date.tomorrowDate.time), tomorrowRatesItems)
            Thread.sleep(15000)
            mergeRates()
            showRates()
        }
    }

    private fun mergeRates(){
        for (i in 0 until todayRatesItems.size-1){
            ratesItems.add(RatesItem(
                todayRatesItems[i].CharCode,
                todayRatesItems[i].Scale,
                todayRatesItems[i].Name,
                todayRatesItems[i].RateToday,
                tomorrowRatesItems[i].RateToday
            ))
        }
    }

    private fun showRates(){
        (rates_list.adapter as RatesAdapter).addDate(date)
        (rates_list.adapter as RatesAdapter).addRates(ratesItems.toList())
    }

    private fun requestDates(){
        val subscription = dateManager.getDate()
            .subscribeOn(Schedulers.io())
            .subscribe (
                { retrievedDate ->
                    //(rates_list.adapter as RatesAdapter).addDate(retrievedDate)
                    date = retrievedDate
                },
                { e ->
                    Snackbar.make(rates_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                }
            )
        subscriptions.add(subscription)
    }

    private fun requestRates(date: String, ratesItem: MutableList<RatesItem>) {

        val subscription = ratesManager.getRates(date)
            .subscribeOn(Schedulers.io())
            .subscribe (
                { retrievedRates ->
                    //(rates_list.adapter as RatesAdapter).addRates(retrievedRates)
                    ratesItem.addAll(retrievedRates)
                },
                { e ->
                    Snackbar.make(rates_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                }
            )

        subscriptions.add(subscription)
    }

    private fun initAdapter() {
        if (rates_list.adapter == null) {
            rates_list.adapter = RatesAdapter()
        }
    }


}