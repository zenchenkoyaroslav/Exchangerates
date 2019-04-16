package com.example.exchangerates.features.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.exchangerates.R
import com.example.exchangerates.commons.RatesItem
import com.example.exchangerates.commons.adapter.ViewType
import com.example.exchangerates.commons.adapter.ViewTypeDelegateAdapter
import com.example.exchangerates.commons.inflate
import kotlinx.android.synthetic.main.rates_item.view.*

class RatesDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RatesViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as RatesViewHolder
        holder.bind(item as RatesItem)
    }

    class RatesViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.rates_item)) {

        fun bind(item: RatesItem) = with(itemView) {
            CharCode.text = item.CharCode
            Name.text = "${item.Scale} ${item.Name}"
            today_rate.text = item.RateToday
            tomorrow_rate.text = item.RateTomorrow
        }
    }
}