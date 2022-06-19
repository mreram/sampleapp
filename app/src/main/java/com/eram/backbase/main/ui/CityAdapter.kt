package com.eram.backbase.main.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.eram.backbase.databinding.ItemCityBinding
import com.eram.backbase.main.util.CityDiffCallback
import com.eram.backbase.model.City

class CityAdapter : ListAdapter<City, CityViewHolder>(CityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}