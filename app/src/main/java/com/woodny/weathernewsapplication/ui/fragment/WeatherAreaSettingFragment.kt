package com.woodny.weathernewsapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.woodny.weathernewsapplication.R
import com.woodny.weathernewsapplication.databinding.FragmentWeatherAreaSettingBinding
import com.woodny.weathernewsapplication.viewmodel.WeatherAreaSettingFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherAreaSettingFragment : Fragment() {
    private val viewModel: WeatherAreaSettingFragmentViewModel by viewModels()
    private lateinit var binding: FragmentWeatherAreaSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherAreaSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // LiveDataのデータをバインドする際にうまく通知させるようにする
        binding.lifecycleOwner = viewLifecycleOwner
        // レイアウトで定義したviewModelに実際のviewModelの参照を渡す
        binding.viewmodel = viewModel

        viewModel.navigate.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { event ->
                when (event) {
                    "ToMain" ->
                        findNavController().navigate(R.id.action_weatherAreaSettingFragment_to_mainFragment)
                }
            }
        }

    }
}