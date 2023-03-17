package com.woodny.weathernewsapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.woodny.weathernewsapplication.R
import java.util.ArrayList

class NewsVerticalAdapter(private val newsTitleList: List<String>):
    RecyclerView.Adapter<NewsVerticalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_list_vertical_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData()
        holder.newsTitle.text = newsTitleList[position]
    }

    override fun getItemCount() = newsTitleList.size

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val newsTitle = item.findViewById<TextView>(R.id.newsTitle)
        val horizontalRecyclerView = item.findViewById<RecyclerView>(R.id.horizontalRecyclerView)

        fun setData() {
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
            horizontalRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            horizontalRecyclerView.adapter = adapter
        }
    }

}