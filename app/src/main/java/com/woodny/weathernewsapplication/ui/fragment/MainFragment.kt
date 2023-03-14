package com.woodny.weathernewsapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.woodny.weathernewsapplication.databinding.FragmentMainBinding
import com.woodny.weathernewsapplication.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainFragmentViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // LiveDataのデータをバインドする際にうまく通知させるようにする
        binding.lifecycleOwner = viewLifecycleOwner
        // レイアウトで定義したviewModelに実際のviewModelの参照を渡す
        binding.viewmodel = viewModel

        viewModel.weatherInfoLiveData.observe(viewLifecycleOwner) {
            val weatherToday =
                it.list[0].temp.day.roundToInt().toString() + "°C<br><font color=#ff0000>" + it.list[0].temp.max.roundToInt().toString() + "°C</font> / <font color=#0000ff>" + it.list[0].temp.min.roundToInt().toString() + "°C</font>"
            binding.weatherTodayText.text = HtmlCompat.fromHtml(weatherToday, FROM_HTML_MODE_COMPACT)
            Glide.with(this)
                .load("https://openweathermap.org/img/wn/" + it.list[0].weather[0].icon + ".png")
                .into(binding.weatherTodayIcon)

            val weatherTomorrow =
                it.list[1].temp.day.roundToInt().toString() + "°C<br><font color=#ff0000>" + it.list[1].temp.max.roundToInt().toString() + "°C</font> / <font color=#0000ff>" + it.list[1].temp.min.roundToInt().toString() + "°C</font>"
            binding.weatherTomorrowText.text = HtmlCompat.fromHtml(weatherTomorrow, FROM_HTML_MODE_COMPACT)
            Glide.with(this)
                .load("https://openweathermap.org/img/wn/" + it.list[1].weather[0].icon + ".png")
                .into(binding.weatherTomorrowIcon)
        }
        viewModel.fetchWeatherInfo()
    }

}