package ru.borodinskiy.aleksei.listcar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.borodinskiy.aleksei.listcar.R
import ru.borodinskiy.aleksei.listcar.adapter.CarAdapter
import ru.borodinskiy.aleksei.listcar.app.ListCarApplication
import ru.borodinskiy.aleksei.listcar.databinding.FragmentCarBinding
import ru.borodinskiy.aleksei.listcar.viewmodel.CarViewModel
import ru.borodinskiy.aleksei.listcar.viewmodel.CarViewModelFactory


class CarFragment : Fragment() {

//    private val viewModel: CarViewModel by activityViewModels()

    private val viewModel: CarViewModel by activityViewModels {
        CarViewModelFactory(
            (activity?.application as ListCarApplication).database.carDao()
        )
    }

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCarBinding.inflate(inflater, container, false)

//        recyclerView = binding.recyclerView
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        val adapter = CarAdapter()
//
//        binding.recyclerView.adapter = adapter
//
//        lifecycle.coroutineScope.launch {
//            viewModel.fullCars().collect() {
//                adapter.submitList(it)
//            }
//        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_carFragment_to_newCarFragment)
        }

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CarAdapter()
        recyclerView.adapter = adapter

        lifecycle.coroutineScope.launch {
            viewModel.fullCars().collect() {
                adapter.submitList(it)
            }
        }

//        lifecycle.coroutineScope.launch {
//            viewModel.priceCar().collect() {
//                adapter.submitList(it)
//            }
//        }


        return binding.root
    }

}