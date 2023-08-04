package ru.borodinskiy.aleksei.listcar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.borodinskiy.aleksei.listcar.R
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

    lateinit var car : Car

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewCarBinding.inflate(inflater, container, false)

        return binding.root
    }

}