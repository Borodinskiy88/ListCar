package ru.borodinskiy.aleksei.listcar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.borodinskiy.aleksei.listcar.R
import ru.borodinskiy.aleksei.listcar.databinding.CardCarBinding
import ru.borodinskiy.aleksei.listcar.entity.Car
import ru.borodinskiy.aleksei.listcar.utils.ReformatValues.reformatCount

interface OnInteractionListener {
    fun onUpdate(car: Car)
    fun onDelete(car: Car)
    fun filterByBrand(car: Car)
    fun filterByModel(car: Car)
    fun filterByPrice()
}

class CarAdapter(
    private val onInteractionListener: OnInteractionListener
) :
    ListAdapter<Car, CarAdapter.CarViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {

        return CarViewHolder(
            CardCarBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onInteractionListener
        )
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class CarViewHolder(
        private val binding: CardCarBinding,
        private val onInteractionListener: OnInteractionListener
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
                price.text = reformatCount(car.price)

                brand.setOnClickListener {
                    Snackbar.make(binding.root, R.string.show_brand, Snackbar.LENGTH_SHORT).show()
                    onInteractionListener.filterByBrand(car)
                }

                model.setOnClickListener {
                    Snackbar.make(binding.root, R.string.show_model, Snackbar.LENGTH_SHORT).show()
                    onInteractionListener.filterByModel(car)
                }

                //TODO Оставить только в верхнем меню
                price.setOnClickListener {
                    Snackbar.make(binding.root, "My Message", Snackbar.LENGTH_SHORT).show()
                    onInteractionListener.filterByPrice()
                }


                menu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.menu_options)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.remove -> {
                                    onInteractionListener.onDelete(car)
                                    true
                                }

                                R.id.edit -> {
                                    onInteractionListener.onUpdate(car)
                                    true
                                }

                                else -> false
                            }
                        }
                    }.show()
                }
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