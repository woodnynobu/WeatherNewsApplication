package com.woodny.weathernewsapplication.ui.adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.woodny.weathernewsapplication.R

class NewsHorizontalAdapter(private val titleList: List<String>, private val authorList: List<String>, private val publishTimeList: List<String>):
    RecyclerView.Adapter<NewsHorizontalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_list_horizontal_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = titleList[position]
        holder.author.text = authorList[position]
        holder.publishTime.text = publishTimeList[position]
        holder.newIcon.visibility = View.VISIBLE
    }

    override fun getItemCount() = titleList.size

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title = item.findViewById<TextView>(R.id.title)
        val author = item.findViewById<TextView>(R.id.author)
        val publishTime = item.findViewById<TextView>(R.id.publishTime)
        val newIcon = item.findViewById<ImageView>(R.id.newIcon)
    }

}