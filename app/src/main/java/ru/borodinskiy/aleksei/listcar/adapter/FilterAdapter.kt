package ru.borodinskiy.aleksei.listcar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.borodinskiy.aleksei.listcar.R
import ru.borodinskiy.aleksei.listcar.databinding.CardCarBinding
import ru.borodinskiy.aleksei.listcar.entity.Car
import ru.borodinskiy.aleksei.listcar.utils.ReformatValues

class FilterAdapter :
    ListAdapter<Car, FilterAdapter.FilterViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {

        return FilterViewHolder(
            CardCarBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class FilterViewHolder(
        private val binding: CardCarBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car) {

            binding.apply {
                when (car.brand) {
                    "BMW" -> {
                        logo.setImageResource(R.drawable.ic_bmw_48)
                    }

                    "Audi" -> {
                        logo.setImageResource(R.drawable.ic_audi_48)
                    }

                    "KIA" -> {
                        logo.setImageResource(R.drawable.ic_kia_48)
                    }

                    else -> logo.setImageResource(R.drawable.ic_wheel_blue_48)
                }

                brand.text = car.brand
                model.text = car.model
                specifications.text = car.specifications
                price.text = ReformatValues.reformatCount(car.price)

                brand.setCompoundDrawables(null, null, null, null)
                model.setCompoundDrawables(null, null, null, null)

                menu.isVisible = false
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Car>() {
            override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
                return oldItem == newItem
            }
        }
    }
}