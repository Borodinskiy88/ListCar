package ru.borodinskiy.aleksei.listcar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.borodinskiy.aleksei.listcar.adapter.FilterAdapter
import ru.borodinskiy.aleksei.listcar.databinding.FragmentCarBinding
import ru.borodinskiy.aleksei.listcar.viewmodel.CarViewModel

@AndroidEntryPoint
class FilterCarFragment : Fragment() {

    companion object {
        const val MODEL = "model"
        const val BRAND = "brand"
        const val SPECIFICATIONS = "specifications"
        const val PRICE = "price"
        const val DECREASE = "decrease"
        const val INCREASE = "increase"
    }

    private var _binding: FragmentCarBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CarViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FilterAdapter()

        recyclerView.adapter = adapter

        binding.topBar.isVisible = false
        binding.searchLabel.isVisible = false
        binding.menuSorted.isVisible = false
        binding.floatingActionButton.isVisible = false

        binding.recyclerView.layoutParams.height = 2650


        val brand = arguments?.getString(BRAND)
        val model = arguments?.getString(MODEL)

        //TODO
        val decrease = arguments?.getString(DECREASE)
        val increase = arguments?.getString(INCREASE)

        if (brand != null) {
            brand.let {
                viewModel.getCarByBrand(it).observe(viewLifecycleOwner) { cars ->
                    cars.let {
                        adapter.submitList(it)
                    }
                }
            }
        } else if (model != null) {
            model.let {
                viewModel.getCarByModel(it).observe(viewLifecycleOwner) { cars ->
                    cars.let {
                        adapter.submitList(it)
                    }
                }
            }
        } else if (decrease != null) {
            viewModel.priceCarDecrease.observe(this.viewLifecycleOwner) { cars ->
                cars.let {
                    adapter.submitList(it)
                }
            }
        } else if (increase != null) {
            viewModel.priceCarIncrease.observe(this.viewLifecycleOwner) { cars ->
                cars.let {
                    adapter.submitList(it)
                }
            }
        }


//        if (brand != null) {
//            brand.let {
//                viewModel.getCarByBrand(it).observe(viewLifecycleOwner) { cars ->
//                    cars.let {
//                        adapter.submitList(it)
//                    }
//                }
//            }
//        } else if (model != null) {
//            model.let {
//                viewModel.getCarByModel(it).observe(viewLifecycleOwner) { cars ->
//                    cars.let {
//                        adapter.submitList(it)
//                    }
//                }
//            }
//        } else {
//            viewModel.priceCarDecrease.observe(this.viewLifecycleOwner) { cars ->
//                cars.let {
//                    adapter.submitList(it)
//                }
//            }
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}