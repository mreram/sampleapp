package com.eram.backbase.main.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.eram.backbase.databinding.ItemCityBinding
import com.eram.backbase.model.City

class CityViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: City) {
        binding.title.text = item.name + ", " + item.country
    }
}