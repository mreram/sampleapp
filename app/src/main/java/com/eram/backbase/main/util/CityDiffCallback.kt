package com.eram.backbase.main.util

import androidx.recyclerview.widget.DiffUtil
import com.eram.backbase.model.City

class CityDiffCallback : DiffUtil.ItemCallback<City>() {

    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }
}