package com.woodny.weathernewsapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.woodny.weathernewsapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LicenseFragment : Fragment() {

    companion object {
        private const val LICENSE_FILE = "file:///android_asset/licenses.html"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_license, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<WebView>(R.id.webView).loadUrl(LICENSE_FILE)
    }

}