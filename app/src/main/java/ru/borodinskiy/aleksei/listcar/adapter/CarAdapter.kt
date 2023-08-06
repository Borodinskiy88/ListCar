package ru.borodinskiy.aleksei.listcar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.borodinskiy.aleksei.listcar.R
import ru.borodinskiy.aleksei.listcar.databinding.CardCarBinding
import ru.borodinskiy.aleksei.listcar.entity.Car

class CarAdapter() :
    ListAdapter<Car, CarAdapter.CarViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {

        return CarViewHolder(
            CardCarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class CarViewHolder(
        private val binding: CardCarBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car) {

            when (car.brand) {
                "BMW" -> {
                    binding.logo.setImageResource(R.drawable.ic_bmw_48)
                }

                "Audi " -> {
                    binding.logo.setImageResource(R.drawable.ic_audi_48)
                }

                "KIA" -> {
                    binding.logo.setImageResource(R.drawable.ic_kia_48)
                }

                else -> binding.logo.setImageResource(R.drawable.ic_wheel_48)
            }

            binding.brand.text = car.brand
            binding.model.text = car.model
            binding.specifications.text = car.specifications
            binding.price.text = car.price.toString()
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