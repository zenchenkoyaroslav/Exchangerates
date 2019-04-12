package com.example.exchangerates

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.rates_fragment.*
import rx.schedulers.Schedulers

class RatesFragment : RxBaseFragment() {

    private val ratesManager by lazy { RatesManager() }
    private val dateManager by lazy { DateManager() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.rates_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rates_list.setHasFixedSize(true)
        rates_list.layoutManager = LinearLayoutManager(context)
        initAdapter()

        if (savedInstanceState == null) {
            requestRates()
        }
    }



    private fun requestRates() {
        val subscription1 = dateManager.getDate()
            .subscribeOn(Schedulers.io())
            .subscribe (
                { retrievedDate ->
                    (rates_list.adapter as RatesAdapter).addDate(retrievedDate)
                },
                { e ->
                    Snackbar.make(rates_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                }
            )
        subscriptions.add(subscription1)
        val subscription2 = ratesManager.getRates()
            .subscribeOn(Schedulers.io())
            .subscribe (
                { retrievedRates ->
                    (rates_list.adapter as RatesAdapter).addRates(retrievedRates)
                },
                { e ->
                    Snackbar.make(rates_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                }
            )

        subscriptions.add(subscription2)
    }

    private fun initAdapter() {
        if (rates_list.adapter == null) {
            rates_list.adapter = RatesAdapter()
        }
    }


}