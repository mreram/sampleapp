package com.eram.backbase.main.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.eram.backbase.databinding.ItemCityBinding
import com.eram.backbase.model.view.CityItem

class CityViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: CityItem) {
        with(binding) {
            title.text = item.name + ", " + item.country
            subTitle.text = "${item.coordinates.latitude}, ${item.coordinates.longitude}"
        }
    }
}