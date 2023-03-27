package com.woodny.weathernewsapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.woodny.weathernewsapplication.databinding.NewsListVerticalItemBinding
import com.woodny.weathernewsapplication.model.data.NewsHorizontalData
import com.woodny.weathernewsapplication.model.data.NewsResponse
import com.woodny.weathernewsapplication.model.data.NewsVerticalData
import com.woodny.weathernewsapplication.viewmodel.MainFragmentViewModel

class NewsVerticalAdapter(
    private val newsTitleList: List<NewsVerticalData>,
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: MainFragmentViewModel
): RecyclerView.Adapter<NewsVerticalAdapter.BindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding = NewsListVerticalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        holder.bind(viewLifecycleOwner, viewModel, position, newsTitleList)
    }

    override fun getItemCount() = newsTitleList.size

    class BindingHolder(private val binding: NewsListVerticalItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(viewLifecycleOwner: LifecycleOwner,
                 viewModel: MainFragmentViewModel,
                 position: Int,
                 newsTitleList: List<NewsVerticalData>) {

            when (position) {
                0 -> {
                    viewModel.newsJapanLiveData.observe(viewLifecycleOwner) { response ->
                        response?.let{
                            updateHorizontalRecyclerView(
                                it,
                                viewModel,
                                newsTitleList[position]
                            )
                        }
                    }
                }
                1 -> {
                    viewModel.newsEntertainmentLiveData.observe(viewLifecycleOwner) { response ->
                        response?.let {
                            updateHorizontalRecyclerView(
                                it,
                                viewModel,
                                newsTitleList[position]
                            )
                        }
                    }
                }
                2 -> {
                    viewModel.newsSportsLiveData.observe(viewLifecycleOwner) { response ->
                        response?.let {
                            updateHorizontalRecyclerView(
                                it,
                                viewModel,
                                newsTitleList[position]
                            )
                        }
                    }
                }
            }
        }

        private fun updateHorizontalRecyclerView(response: NewsResponse,
                                                 viewModel: MainFragmentViewModel,
                                                 newsTitleList: NewsVerticalData) {
            binding.data = newsTitleList

            val newsHorizontalList = response.value?.map {article ->
                NewsHorizontalData(
                    article.name,
                    article.provider[0].name,
                    article.url,
                    article.datePublished,
                    article.image?.thumbnail?.contentUrl
                )
            }

            newsHorizontalList?.let {
                val adapter = NewsHorizontalAdapter(it, viewModel)
                // linearLayoutManager と adapter をRecyclerViewにセット
                binding.horizontalRecyclerView.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                binding.horizontalRecyclerView.adapter = adapter
            }
        }

    }

}