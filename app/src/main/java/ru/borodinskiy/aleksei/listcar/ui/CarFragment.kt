package ru.borodinskiy.aleksei.listcar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.borodinskiy.aleksei.listcar.R
import ru.borodinskiy.aleksei.listcar.adapter.CarAdapter
import ru.borodinskiy.aleksei.listcar.adapter.OnInteractionListener
import ru.borodinskiy.aleksei.listcar.databinding.FragmentCarBinding
import ru.borodinskiy.aleksei.listcar.entity.Car
import ru.borodinskiy.aleksei.listcar.viewmodel.CarViewModel

@AndroidEntryPoint
class CarFragment : Fragment(), MenuProvider {

    lateinit var car: Car

    private val viewModel: CarViewModel by activityViewModels()

    private var _binding: FragmentCarBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: CarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CarAdapter(object : OnInteractionListener {

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

            override fun filterByBrand(car: Car) {
                val bundle = bundleOf(
                    Pair("brand", car.brand),
                )
                findNavController().navigate(R.id.filterCarFragment, bundle)
            }

            override fun filterByModel(car: Car) {
                val bundle = bundleOf(
                    Pair("model", car.model),
                )
                findNavController().navigate(R.id.filterCarFragment, bundle)
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
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {

            R.id.menu_sorted_decrease -> {
                val bundle = bundleOf(
                    Pair("decrease", "decrease"),
                )
                Snackbar.make(binding.root, R.string.show_decrease, Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.filterCarFragment, bundle)
                true
            }

            R.id.menu_sorted_increase -> {
                val bundle = bundleOf(
                    Pair("increase", "increase"),
                )
                Snackbar.make(binding.root, R.string.show_increase, Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.filterCarFragment, bundle)
                true
            }

            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}