package com.woodny.weathernewsapplication.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.woodny.weathernewsapplication.R

object BindingAdapters {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun bindImage(imageView: ImageView, imageUrl: String?) {
        if (imageUrl.isNullOrEmpty()) {
            imageView.setImageResource(R.drawable.baseline_image_not_supported_48)
        } else {
            val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(imageView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.baseline_downloading_48)
                        .error(R.drawable.baseline_image_not_supported_48))
                .into(imageView)
        }
    }

    @BindingAdapter("convertDate")
    @JvmStatic
    fun bindConvertDate(textView: TextView, publishDate: String?) {
        publishDate?.let {
            val convertDate = it.replace("T", " ")
                .replaceAfter("." , "")
                .dropLast(1)
            textView.text = convertDate
        }

    }


}