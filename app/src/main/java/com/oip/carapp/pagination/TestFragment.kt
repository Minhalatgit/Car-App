package com.oip.carapp.pagination

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.oip.carapp.R
import com.oip.carapp.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.fragment_test.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestFragment : Fragment(R.layout.fragment_test) {

    private val TAG = "TestFragment"

    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1
    private var totalPages = 0
    var list = ArrayList<CampaignData>()
    private lateinit var adapter: PaginationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = PaginationAdapter(requireContext(), list)
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.adapter = adapter

        recyclerview.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                loadNextPage()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

        loadFirstPage()
    }

    private fun loadNextPage() {
        Log.d(TAG, "loadNextPage with current page $currentPage")
        RetrofitClient.retrofitClientTwo.getCampaigns(currentPage.toString())
            .enqueue(object : Callback<Campaign> {
                override fun onResponse(
                    call: Call<Campaign>,
                    response: Response<Campaign>
                ) {
                    adapter.removeLoadingFooter()
                    isLoading = false

                    list = response.body()?.data?.campaignDataList!!
                    adapter.addAll(list)

                    if (currentPage != totalPages) adapter.addLoadingFooter();
                    else isLastPage = true
                }

                override fun onFailure(call: Call<Campaign>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    private fun loadFirstPage() {
        Log.d(TAG, "loadFirstPage with current page $currentPage")
        progressbar.visibility = View.VISIBLE
        RetrofitClient.retrofitClientTwo.getCampaigns(currentPage.toString())
            .enqueue(object : Callback<Campaign> {
                override fun onResponse(
                    call: Call<Campaign>,
                    response: Response<Campaign>
                ) {
                    list = response.body()?.data?.campaignDataList!!
                    progressbar.visibility = View.GONE
                    adapter.addAll(list)

                    totalPages = response.body()?.data?.lastPage!!

                    if (currentPage <= totalPages) adapter.addLoadingFooter()
                    else isLastPage = true
                }

                override fun onFailure(call: Call<Campaign>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }
}