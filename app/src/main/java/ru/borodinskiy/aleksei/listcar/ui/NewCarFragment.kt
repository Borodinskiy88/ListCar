package ru.borodinskiy.aleksei.listcar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.borodinskiy.aleksei.listcar.R
import ru.borodinskiy.aleksei.listcar.databinding.FragmentNewCarBinding
import ru.borodinskiy.aleksei.listcar.utils.AndroidUtils
import ru.borodinskiy.aleksei.listcar.viewmodel.CarViewModel

@AndroidEntryPoint
class NewCarFragment : Fragment() {

    companion object {
        const val MODEL = "model"
        const val BRAND = "brand"
        const val SPECIFICATIONS = "specifications"
        const val PRICE = "price"
    }

    private var _binding: FragmentNewCarBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CarViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewCarBinding.inflate(inflater, container, false)


        binding.apply {
            brandText.setText(arguments?.getString(BRAND))
            modelText.setText(arguments?.getString(MODEL))
            specificationsText.setText(arguments?.getString(SPECIFICATIONS))
            priceText.setText(arguments?.getString(PRICE))
        }

        binding.save.setOnClickListener {
            if (binding.brandText.text.toString().isBlank() ||
                binding.modelText.text.toString().isBlank() ||
                binding.specificationsText.text.toString().isBlank() ||
                binding.priceText.text.toString().isBlank()
            ) {
                Snackbar.make(
                    binding.root,
                    R.string.fill_in_all_fields,
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            viewModel.changeCar(
                binding.brandText.text.toString(),
                binding.modelText.text.toString(),
                binding.specificationsText.text.toString(),
                binding.priceText.text.toString()
            )
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigate(R.id.action_newCarFragment_to_carFragment)
            viewLifecycleOwner
        }

//        viewModel.created.observe(viewLifecycleOwner) {
//            viewModel.clear()
//            findNavController().navigateUp()
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}