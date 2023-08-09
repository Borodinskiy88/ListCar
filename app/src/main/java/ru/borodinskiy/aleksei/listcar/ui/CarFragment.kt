package ru.borodinskiy.aleksei.listcar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.borodinskiy.aleksei.listcar.R
import ru.borodinskiy.aleksei.listcar.adapter.CarAdapter
import ru.borodinskiy.aleksei.listcar.adapter.OnInteractionListener
import ru.borodinskiy.aleksei.listcar.databinding.FragmentCarBinding
import ru.borodinskiy.aleksei.listcar.entity.Car
import ru.borodinskiy.aleksei.listcar.viewmodel.CarViewModel

@AndroidEntryPoint
class CarFragment : Fragment() {

    lateinit var car: Car

    private val viewModel: CarViewModel by activityViewModels()

    private var _binding: FragmentCarBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CarAdapter(object : OnInteractionListener {

            override fun onUpdate(car: Car) {
                viewModel.update(car)
                val bundle = bundleOf(
                    Pair("model", car.model),
                    Pair("brand", car.brand),
                    Pair("specifications", car.specifications),
                    Pair("price", car.price.toString())
                )
                findNavController().navigate(R.id.action_carFragment_to_newCarFragment, bundle)
            }

            override fun onDelete(car: Car) {
                viewModel.delete(car)
            }

        })
        recyclerView.adapter = adapter

        viewModel.allCars.observe(this.viewLifecycleOwner) { cars ->
            cars.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_carFragment_to_newCarFragment)
        }

        binding.menuSorted.setOnClickListener { it ->
            PopupMenu(it.context, it).apply {
                inflate(R.menu.menu_sort)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.decrease -> {
                            viewModel.priceCarDecrease.observe(viewLifecycleOwner) { items ->
                                items.let {
                                    adapter.submitList(it)
                                }
                            }
                            true
                        }

                        R.id.increase -> {
                            viewModel.priceCarIncrease.observe(viewLifecycleOwner) { items ->
                                items.let {
                                    adapter.submitList(it)
                                }
                            }
                            true
                        }

                        else -> false
                    }
                }
            }.show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}