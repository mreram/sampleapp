package com.eram.backbase.main.util

import androidx.recyclerview.widget.DiffUtil
import com.eram.backbase.model.view.CityItem

class CityDiffCallback : DiffUtil.ItemCallback<CityItem>() {

    override fun areItemsTheSame(oldItem: CityItem, newItem: CityItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CityItem, newItem: CityItem): Boolean {
        return oldItem == newItem
    }
}