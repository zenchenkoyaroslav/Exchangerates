package com.example.exchangerates.features.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.exchangerates.R
import com.example.exchangerates.commons.adapter.ViewType
import com.example.exchangerates.commons.adapter.ViewTypeDelegateAdapter
import com.example.exchangerates.commons.inflate

class NotFoundDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) =
        NotFoundViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {

    }

    class NotFoundViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder (parent.inflate(R.layout.not_found_item))
}