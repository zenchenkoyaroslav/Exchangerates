package com.example.exchangerates

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.date_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class DateDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = DateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as DateViewHolder
        holder.bind(item as DateItem)
    }

    class DateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder (
        parent.inflate(R.layout.date_item)){

        val format = SimpleDateFormat("MM.dd.YYYY")

        fun bind(item: DateItem) = with(itemView) {
            //today_date.text = "${item.todayDate.get(Calendar.MONTH)}.${item.todayDate.get(Calendar.DAY_OF_MONTH)}.${item.todayDate.get(Calendar.YEAR)}"
            today_date.text = format.format(item.todayDate.time)
            tomorrow_date.text = format.format(item.tomorrowDate.time)
        }
    }
}