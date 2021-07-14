package com.oip.carapp.home.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentServiceListBinding
import com.oip.carapp.home.adapters.CarServiceAdapter
import com.oip.carapp.home.models.ServiceResponse
import com.oip.carapp.home.viewmodel.ServiceListViewModel
import com.oip.carapp.utils.hideProgressBar
import com.oip.carapp.utils.showProgressBar
import java.util.ArrayList

class ServiceListFragment : BaseFragment(), CarServiceAdapter.ServiceListener {

    private val TAG = "ServiceListFragment"

    private lateinit var binding: FragmentServiceListBinding
    private lateinit var args: ServiceListFragmentArgs
    private lateinit var serviceListRV: RecyclerView
    private lateinit var adapter: CarServiceAdapter

    private lateinit var viewModel: ServiceListViewModel
    private var list = ArrayList<ServiceResponse>()

    private var lastFavouritePosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceListBinding.inflate(layoutInflater, container, false)

        // View model init
        viewModel = ViewModelProvider(this).get(ServiceListViewModel::class.java)

        // Observing service list and updating Recycler view
        viewModel.serviceList.observe(viewLifecycleOwner, Observer {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
            hideProgressBar(window, binding.progress)
        })

        // Observing no data object
        viewModel.noService.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                binding.notAvailable.visibility = View.VISIBLE
//            } else {
//                binding.notAvailable.visibility = View.GONE
//            }
        })

        // Observing favourite updated
        viewModel.favouriteUpdated.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreateView: Favourite updated $lastFavouritePosition")
            if (it) {
                list[lastFavouritePosition].isFavourite = "1"
            } else {
                list[lastFavouritePosition].isFavourite = "0"
            }
            adapter.notifyItemChanged(lastFavouritePosition)
        })

        // getting service type and title
        args = ServiceListFragmentArgs.fromBundle(requireArguments())
        binding.title.text = args.serviceType

        // Recycler View setup
        serviceListRV = binding.serviceList
        (serviceListRV.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        serviceListRV.layoutManager = LinearLayoutManager(requireActivity())
        adapter = CarServiceAdapter(list, requireContext(), this)
        serviceListRV.adapter = adapter

        // show progress and get service list data from API
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

    override fun onFavouriteClick(position: Int) {
        lastFavouritePosition = position
        if (list[position].isFavourite == "0") {
            viewModel.updateFavourite(list[position].id, "1")
        } else {
            viewModel.updateFavourite(list[position].id, "0")
        }
    }

}