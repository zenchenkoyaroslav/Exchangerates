package com.example.exchangerates

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class RatesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegateAdapters.put(AdapterConstants.DATE, DateDelegateAdapter())
        delegateAdapters.put(AdapterConstants.RATES, RatesDelegateAdapter())
        items = ArrayList()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType)!!.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))?.onBindViewHolder(holder, this.items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return this.items.get(position).getViewType()
    }

    fun addRates(rates: List<RatesItem>) {
        val initPosition = items.size - 1

        items.addAll(rates)
        notifyItemRangeChanged(initPosition, items.size + 1)
    }

    fun addDate(date: DateItem){
        //val initPosition = items.size - 1

        items.add(date)
        //notifyItemRangeChanged(initPosition, items.size)
    }
/*
    fun clearAndAddRates(news: List<RatesItem>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())


        items.addAll(news)
        notifyItemRangeInserted(0, items.size)
    }
*/
    fun getRates(): List<RatesItem> {
        return items
            .filter { it.getViewType() == AdapterConstants.RATES }
            .map { it as RatesItem }
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}