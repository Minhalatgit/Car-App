package com.oip.carapp.home.adapters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentAllCarBinding
import com.oip.carapp.home.models.Car

class AllCarFragment : Fragment(), ChooseCarAdapter.ChooseCarListener {

    private lateinit var binding: FragmentAllCarBinding
    private lateinit var carsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllCarBinding.inflate(inflater, container, false)

        carsRecyclerView = binding.carsRecyclerView
        carsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val list = ArrayList<Car>()
        list.add(
            Car(
                R.drawable.car,
                "BMW 3 Series",
                "$ 123 / Day",
                "5 Seater  -  Diesel  -  Automatic"
            )
        )
        list.add(
            Car(
                R.drawable.car,
                "BMW 3 Series",
                "$ 123 / Day",
                "5 Seater  -  Diesel  -  Automatic"
            )
        )
        list.add(
            Car(
                R.drawable.car,
                "BMW 3 Series",
                "$ 123 / Day",
                "5 Seater  -  Diesel  -  Automatic"
            )
        )
        list.add(
            Car(
                R.drawable.car,
                "BMW 3 Series",
                "$ 123 / Day",
                "5 Seater  -  Diesel  -  Automatic"
            )
        )
        list.add(
            Car(
                R.drawable.car,
                "BMW 3 Series",
                "$ 123 / Day",
                "5 Seater  -  Diesel  -  Automatic"
            )
        )
        list.add(
            Car(
                R.drawable.car,
                "BMW 3 Series",
                "$ 123 / Day",
                "5 Seater  -  Diesel  -  Automatic"
            )
        )

        carsRecyclerView.adapter = ChooseCarAdapter(
            list,
            requireContext(),
            this
        )

        return binding.root
    }

    override fun onCarClick(position: Int) {
        Log.d("AllCarFragment", "onCarClick: $position")
    }
}