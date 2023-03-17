package com.woodny.weathernewsapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.woodny.weathernewsapplication.databinding.NewsListVerticalItemBinding
import com.woodny.weathernewsapplication.model.data.NewsVerticalData
import java.util.ArrayList

class NewsVerticalAdapter(private val newsTitleList: List<NewsVerticalData>):
    RecyclerView.Adapter<NewsVerticalAdapter.BindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding = NewsListVerticalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        holder.setData(newsTitleList[position])
    }

    override fun getItemCount() = newsTitleList.size

    class BindingHolder(private val binding: NewsListVerticalItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(newsTitle: NewsVerticalData) {
            binding.data = newsTitle

            // TODO:APIで取得した情報を設定する
            val titleList = ArrayList<String>()
            titleList.add("スクエニ松田社長、『フォースポークン』発売直後のレビュー・販売状況を「厳しい」と認めていた。でもまだこれから - AUTOMATON")
            titleList.add("いいいいいいいいいい")
            titleList.add("うううううううううう")
            titleList.add("えええええええええ")
            titleList.add("おおおおおおおおおおおおおおお")

            val authorList = ArrayList<String>()
            authorList.add("あああああああああああ")
            authorList.add("いいいいいいいいいい")
            authorList.add("うううううううううう")
            authorList.add("えええええええええ")
            authorList.add("おおおおおおおおおおおおおおお")

            val publishTimeList = ArrayList<String>()
            publishTimeList.add("2023-03-11T09:53:48Z")
            publishTimeList.add("2023-03-11T09:53:48Z")
            publishTimeList.add("2023-03-11T09:53:48Z")
            publishTimeList.add("2023-03-11T09:53:48Z")
            publishTimeList.add("2023-03-11T09:53:48Z")

            val adapter = NewsHorizontalAdapter(titleList, authorList, publishTimeList)
            // linearLayoutManager と adapter をRecyclerViewにセット
            binding.horizontalRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            binding.horizontalRecyclerView.adapter = adapter
        }
    }

}