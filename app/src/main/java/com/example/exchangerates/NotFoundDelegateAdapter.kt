package com.example.exchangerates

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class NotFoundDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = NotFoundViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {

    }

    class NotFoundViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder (parent.inflate(R.layout.not_found_item))
}