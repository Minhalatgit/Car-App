package com.oip.carapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentServiceListBinding
import com.oip.carapp.home.adapters.CarServiceAdapter
import com.oip.carapp.home.models.ServiceResponse
import com.oip.carapp.home.viewmodel.ServiceListViewModel
import com.oip.carapp.utils.hideProgressBar
import com.oip.carapp.utils.showProgressBar

class ServiceListFragment : BaseFragment(), CarServiceAdapter.ServiceListener {

    private lateinit var binding: FragmentServiceListBinding
    private lateinit var args: ServiceListFragmentArgs
    private lateinit var serviceListRV: RecyclerView

    private lateinit var viewModel: ServiceListViewModel
    private lateinit var list: List<ServiceResponse>

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
            list = it
            hideProgressBar(window, binding.progress)
            serviceListRV.adapter = CarServiceAdapter(it, requireContext(), this)
        })

        viewModel.noService.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                binding.notAvailable.visibility = View.VISIBLE
//            } else {
//                binding.notAvailable.visibility = View.GONE
//            }
        })

        showProgressBar(window, binding.progress)
        viewModel.getServices(args.catId)

        return binding.root
    }

    override fun onServiceClick(position: Int) {
        Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            .navigate(
                ServiceListFragmentDirections.actionServiceListFragmentToServiceBookingFragment(
                    list[position]
                )
            )
    }

}