package com.oip.carapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PaginationAdapter(private var context: Context, private var campaignList: ArrayList<CampaignData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val LOADING = 0
        private const val ITEM = 1
    }

    private var isLoading = false

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
    }

    class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.loader)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        viewHolder = when (viewType) {
            ITEM -> {
                val viewItem = inflater.inflate(R.layout.movie_item, parent, false)
                MovieViewHolder(viewItem)
            }
            LOADING -> {
                val viewItem = inflater.inflate(R.layout.loader_item, parent, false)
                ProgressViewHolder(viewItem)
            }
            else -> {
                Log.d("PaginationAdapter", "Else block for create view holder")
                MovieViewHolder(inflater.inflate(R.layout.movie_item, parent, false))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val campaignData = campaignList[position]
        when (getItemViewType(position)) {
            ITEM -> {
                val movieHolder = holder as MovieViewHolder
                movieHolder.movieTitle.text = campaignData.title
                Picasso.get().load(campaignData.imagePath).into(holder.movieImage)
            }
            LOADING -> {
                val loadingHolder = holder as ProgressViewHolder
                loadingHolder.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount() = campaignList.size

    override fun getItemViewType(position: Int): Int {
        return if (position == campaignList.size - 1 && isLoading) LOADING else ITEM
    }

    fun addLoadingFooter() {
        isLoading = true
        add(CampaignData())
    }

    fun removeLoadingFooter() {
        isLoading = false

        val position = campaignList.size - 1
        val result = getItem(position)

        if (result != null) {
            campaignList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun add(campaign: CampaignData) {
        campaignList.add(campaign)
        notifyItemInserted(campaignList.size - 1)
    }

    fun addAll(list: ArrayList<CampaignData>) {
        for (item in list) {
            add(item)
        }
    }

    fun getItem(position: Int): CampaignData {
        return campaignList.get(position)
    }
}