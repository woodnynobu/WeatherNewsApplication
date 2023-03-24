package com.woodny.weathernewsapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.woodny.weathernewsapplication.R
import com.woodny.weathernewsapplication.databinding.NewsListHorizontalItemBinding
import com.woodny.weathernewsapplication.model.data.NewsHorizontalData
import com.woodny.weathernewsapplication.viewmodel.MainFragmentViewModel

class NewsHorizontalAdapter(
    private val newsHorizontalList: List<NewsHorizontalData>,
    private val viewModel: MainFragmentViewModel
): RecyclerView.Adapter<NewsHorizontalAdapter.BindHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindHolder {
        val binding = NewsListHorizontalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindHolder(binding)
    }

    override fun onBindViewHolder(holder: BindHolder, position: Int) {
        holder.setData(newsHorizontalList[position], viewModel)
    }

    override fun getItemCount() = newsHorizontalList.size

    class BindHolder(private val binding: NewsListHorizontalItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(newsHorizontalData:NewsHorizontalData, viewModel: MainFragmentViewModel) {
            binding.data = newsHorizontalData
            binding.viewModel = viewModel
        }
    }
}