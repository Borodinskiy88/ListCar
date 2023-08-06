package ru.borodinskiy.aleksei.listcar.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.borodinskiy.aleksei.listcar.app.ListCarApplication
import ru.borodinskiy.aleksei.listcar.databinding.FragmentNewCarBinding
import ru.borodinskiy.aleksei.listcar.entity.Car
import ru.borodinskiy.aleksei.listcar.viewmodel.CarViewModel
import ru.borodinskiy.aleksei.listcar.viewmodel.CarViewModelFactory

class NewCarFragment : Fragment() {

    //TODO
//    private val viewModel: CarViewModel by activityViewModels ()

    private val viewModel: CarViewModel by activityViewModels {
        CarViewModelFactory(
            (activity?.application as ListCarApplication).database
                .carDao()
        )
    }

    private var _binding: FragmentNewCarBinding? = null
    private val binding get() = _binding!!


    lateinit var car: Car

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewCarBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.brandText.text.toString(),
            binding.modelText.text.toString(),
            binding.specificationsText.text.toString(),
        )
    }

//    private fun addNewCar() {
//        if (isEntryValid()) {
//            viewModel.addNewCar(
//                binding.brandText.text.toString(),
//                binding.modelText.text.toString(),
//                binding.specificationsText.text.toString(),
//                binding.priceText.text.toString().toInt()
//            )
//            findNavController().navigate(R.id.carFragment)
//        }
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.save.setOnClickListener {
//            addNewCar()
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }

}