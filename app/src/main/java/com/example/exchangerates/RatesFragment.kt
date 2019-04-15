package com.example.exchangerates

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.rates_fragment.*
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class RatesFragment : RxBaseFragment() {

    private val DATE = "DATE"
    private val RATES = "RATES"


    private lateinit var appSharedPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor
    private val gson = Gson()

    private val ratesManager by lazy { RatesManager() }
    private val dateManager by lazy { DateManager() }

    private var format = SimpleDateFormat("MM.dd.YYYY")
    private lateinit var date: DateItem
    private lateinit var today: Calendar
    private lateinit var yesterday: Calendar
    private var yesterdayRatesItems = mutableListOf<RatesItem>()
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

        appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefsEditor = appSharedPrefs.edit()
        date = requestDates(Calendar.getInstance())
        //Log.e("Date", date.toString())
        if (savedInstanceState == null) {
            if(appSharedPrefs.contains(DATE)){
                var oldDateJson = appSharedPrefs.getString(DATE, "")
                var oldDate = gson.fromJson(oldDateJson, DateItem::class.java)
                if(format.format(oldDate.todayDate.time) == format.format(Calendar.getInstance().time)){
                    //Log.e("Chek", "there isn't request")
                    getOldRates()
                } else {
                    //Log.e("Chek", "there is request, but there was datas")
                    fullRequest()
                }
            }else {
                //Log.e("Chek", "there is request")
                fullRequest()
            }
            if(Globals.isSuccess) {
                showRates()
                saveRates()
            }
        }
    }

    private fun getOldRates() {
        var oldRatesItemJson = appSharedPrefs.getString(RATES, "")
        val ratesType = object : TypeToken<MutableList<RatesItem>>(){}.type
        var oldRatesItem = gson.fromJson<MutableList<RatesItem>>(oldRatesItemJson, ratesType)
        ratesItems = oldRatesItem
    }

    private fun fullRequest(){
        today = Calendar.getInstance()
        yesterday = (today.clone() as Calendar).also {
            it.add(Calendar.DAY_OF_MONTH, -1)
        }
        requestRates(format.format(yesterday.time), yesterdayRatesItems)
        requestRates(format.format(date.todayDate.time), todayRatesItems)
        requestRates(format.format(date.tomorrowDate.time), tomorrowRatesItems)
        Thread.sleep(15000)
        mergeRates()
        if(ratesItems.size == 0) {
            (rates_list.adapter as RatesAdapter).addNotFound()
            Globals.isSuccess = false
            activity?.invalidateOptionsMenu()
        }

    }



    private fun saveRates() {
        var dateJson = gson.toJson(date)
        var ratesJson = gson.toJson(ratesItems)
        prefsEditor.putString(DATE, dateJson)
        prefsEditor.putString(RATES, ratesJson)
        prefsEditor.commit()
    }

    private fun mergeRates(){
        if(tomorrowRatesItems.size == 0) {
            date = requestDates(yesterday)
            for (i in 0 until yesterdayRatesItems.size - 1) {
                ratesItems.add(
                    RatesItem(
                        yesterdayRatesItems[i].CharCode,
                        yesterdayRatesItems[i].Scale,
                        yesterdayRatesItems[i].Name,
                        yesterdayRatesItems[i].RateToday,
                        todayRatesItems[i].RateToday
                    )
                )
            }
        }else{
            for (i in 0 until todayRatesItems.size - 1) {
                ratesItems.add(
                    RatesItem(
                        todayRatesItems[i].CharCode,
                        todayRatesItems[i].Scale,
                        todayRatesItems[i].Name,
                        todayRatesItems[i].RateToday,
                        tomorrowRatesItems[i].RateToday
                    )
                )
            }
        }
    }

    private fun showRates(){
        (rates_list.adapter as RatesAdapter).addDate(date)
        (rates_list.adapter as RatesAdapter).addRates(ratesItems)
    }

    private fun requestDates(calendar: Calendar): DateItem{
        return dateManager.getDate(calendar)
    }

    private fun requestRates(date: String, ratesItem: MutableList<RatesItem>) {

        val subscription = ratesManager.getRates(date)
            .subscribeOn(Schedulers.io())
            .subscribe (
                { retrievedRates ->
                    //(rates_list.adapter as RatesAdapter).addRates(retrievedRates)
                    if(retrievedRates != null){
                        ratesItem.addAll(retrievedRates)
                    }

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