package com.woodny.weathernewsapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.woodny.weathernewsapplication.R
import com.woodny.weathernewsapplication.databinding.FragmentMainBinding
import com.woodny.weathernewsapplication.model.data.NewsVerticalData
import com.woodny.weathernewsapplication.ui.adapter.NewsVerticalAdapter
import com.woodny.weathernewsapplication.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.LocalDate
import timber.log.Timber
import java.util.*
import javax.inject.Inject
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
    @Inject
    lateinit var analytics: FirebaseAnalytics
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
    ): View {
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

        // 画面表示時点でLoading開始
        executeLoading(true)

        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, MainFragment::class.java.simpleName)
        }

        // LiveDataのデータをバインドする際にうまく通知させるようにする
        binding.lifecycleOwner = viewLifecycleOwner
        // レイアウトで定義したviewModelに実際のviewModelの参照を渡す
        binding.viewmodel = viewModel

        binding.title.text = getDateAndDayOfWeek()

        viewModel.area.observe(viewLifecycleOwner) {
            binding.areaButton.text = it
        }

        viewModel.weatherInfoLiveData.observe(viewLifecycleOwner) {weatherInfoResponse ->
            weatherInfoResponse?.let{
                val weatherToday =
                    it.daily[0].temp.day.roundToInt().toString() +
                            "°C<br><font color=#ff0000>" +
                            it.daily[0].temp.max.roundToInt().toString() +
                            "°C</font> / <font color=#0000ff>" +
                            it.daily[0].temp.min.roundToInt().toString() +
                            "°C</font>"
                binding.weatherTodayText.text = HtmlCompat.fromHtml(weatherToday, FROM_HTML_MODE_COMPACT)

                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + it.daily[0].weather[0].icon + ".png")
                    .into(binding.weatherTodayIcon)

                val weatherTomorrow =
                    it.daily[1].temp.day.roundToInt().toString() +
                            "°C<br><font color=#ff0000>" +
                            it.daily[1].temp.max.roundToInt().toString() +
                            "°C</font> / <font color=#0000ff>" +
                            it.daily[1].temp.min.roundToInt().toString() + "°C</font>"
                binding.weatherTomorrowText.text = HtmlCompat.fromHtml(weatherTomorrow, FROM_HTML_MODE_COMPACT)

                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + it.daily[1].weather[0].icon + ".png")
                    .into(binding.weatherTomorrowIcon)
            }
        }

        viewModel.navigate.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { event ->
                when(event) {
                    "ToWeatherAreaSetting" ->
                        findNavController().navigate(R.id.action_mainFragment_to_weatherAreaSettingFragment)
                    "ToNewsDetail" -> {
                        Timber.d("News Url:" + viewModel.getNewsUrl())
                        val action = MainFragmentDirections.actionMainFragmentToNewsDetailFragment(viewModel.getNewsUrl())
                        findNavController().navigate(action)
                    }
                    "ToSettings" ->
                        findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
                    "ToReload" -> {
                        reload()
                    }
                }
            }
        }

        val newsTitleList = listOf(
            NewsVerticalData("一般"),
            NewsVerticalData("エンタメ"),
            NewsVerticalData("スポーツ"),
        )

        val recyclerView = binding.verticalRecyclerView
        val adapter = NewsVerticalAdapter(newsTitleList, viewLifecycleOwner, viewModel)
        // linearLayoutManager と adapter をRecyclerViewにセット
        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        viewModel.isLoadingStop.observe(viewLifecycleOwner) {
            executeLoading(false)
        }
    }

    private fun getDateAndDayOfWeek(): String {
        // 日付の取得（ThreeTenABPライブラリで取得）
        val today = LocalDate.now()

        // 曜日の取得
        val calendar: Calendar = Calendar.getInstance()
        val dayOfWeek = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "日曜日"
            Calendar.MONDAY -> "月曜日"
            Calendar.TUESDAY -> "火曜日"
            Calendar.WEDNESDAY -> "水曜日"
            Calendar.THURSDAY -> "木曜日"
            Calendar.FRIDAY -> "金曜日"
            Calendar.SATURDAY -> "土曜日"
            else -> "該当なし"
        }
        return "$today $dayOfWeek"
    }

    private fun executeLoading(isStart: Boolean) {
        if(isStart) {
            binding.reloadButton.visibility = GONE
            Glide.with(this)
                .load(R.raw.glowing_ring)
                .into(binding.loading)
        } else {
            binding.reloadButton.visibility = VISIBLE
            Glide.with(this)
                .load("")
                .into(binding.loading)
        }
    }

    private fun reload() {
        // 初期化
        viewModel.resetWeatherAndNews()
        // Loading開始
        executeLoading(true)
        viewModel.fetchWeatherAndNews()
    }

}