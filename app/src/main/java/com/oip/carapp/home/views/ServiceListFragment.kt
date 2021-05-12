package com.oip.carapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentServiceListBinding
import com.oip.carapp.home.adapters.CarServiceAdapter
import com.oip.carapp.home.viewmodel.ServiceListViewModel
import com.oip.carapp.utils.hideProgressBar
import com.oip.carapp.utils.showProgressBar

class ServiceListFragment : BaseFragment(), CarServiceAdapter.ServiceListener {

    private lateinit var binding: FragmentServiceListBinding
    lateinit var args: ServiceListFragmentArgs
    private lateinit var serviceListRV: RecyclerView

    private lateinit var viewModel: ServiceListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceListBinding.inflate(layoutInflater, container, false)
        window.statusBarColor = requireActivity().getColor(R.color.white)

        args = ServiceListFragmentArgs.fromBundle(requireArguments())
        binding.title.text = args.serviceType

        serviceListRV = binding.serviceList
        serviceListRV.layoutManager = LinearLayoutManager(requireActivity())

        viewModel = ViewModelProvider(this).get(ServiceListViewModel::class.java)

        viewModel.serviceList.observe(viewLifecycleOwner, Observer {
            hideProgressBar(window, binding.progress)
            serviceListRV.adapter = CarServiceAdapter(it, requireContext(), this)
        })

        showProgressBar(window, binding.progress)
        viewModel.getServices(args.catId)

        return binding.root
    }

    override fun onServiceClick(position: Int) {

    }

}