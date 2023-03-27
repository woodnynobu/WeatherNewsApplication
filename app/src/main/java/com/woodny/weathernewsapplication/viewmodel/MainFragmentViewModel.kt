package com.woodny.weathernewsapplication.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woodny.weathernewsapplication.event.Event
import com.woodny.weathernewsapplication.model.data.NewsHorizontalData
import com.woodny.weathernewsapplication.model.data.NewsResponse
import com.woodny.weathernewsapplication.model.data.WeatherInfoResponse
import com.woodny.weathernewsapplication.model.repository.ClientApiRepository
import com.woodny.weathernewsapplication.model.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val clientApiRepository: ClientApiRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private var _weatherInfoLiveData = MutableLiveData<WeatherInfoResponse?>()
    val weatherInfoLiveData: LiveData<WeatherInfoResponse?> = _weatherInfoLiveData

    private val _navigate = MutableLiveData<Event<String>>()
    val navigate : LiveData<Event<String>>
        get() = _navigate

    private val _area = MutableLiveData<String>()
    val area: LiveData<String> = _area

    private var _newsJapanLiveData = MutableLiveData<NewsResponse?>()
    val newsJapanLiveData: LiveData<NewsResponse?> = _newsJapanLiveData

    private var _newsEntertainmentLiveData = MutableLiveData<NewsResponse?>()
    val newsEntertainmentLiveData: LiveData<NewsResponse?> = _newsEntertainmentLiveData

    private var _newsSportsLiveData = MutableLiveData<NewsResponse?>()
    val newsSportsLiveData: LiveData<NewsResponse?> = _newsSportsLiveData

    val isLoadingStop :LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        val observer = Observer<NewsResponse?> {
            val newsJapan = newsJapanLiveData.value?.value
            val newsEntertainment = newsEntertainmentLiveData.value?.value
            val newsSports = newsSportsLiveData.value?.value
            if (!newsJapan.isNullOrEmpty() &&
                !newsEntertainment.isNullOrEmpty() &&
                !newsSports.isNullOrEmpty()) {
                Timber.d("=Test= isLoadingStop value:true")
                value = true
            }
        }
        addSource(newsJapanLiveData, observer)
        addSource(newsEntertainmentLiveData, observer)
        addSource(newsSportsLiveData, observer)
    }

    private lateinit var _newsUrl: String

    init {
        fetchWeatherAndNews()
    }

    private fun fetchWeatherInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val city = dataStoreRepository.loadCity().first()
            _area.postValue(city)

            val geoCodingInfo = clientApiRepository.fetchGeoCodingInfo(city)
            geoCodingInfo?.let {geoCoding ->
                // 先頭番地のみ取得
                val lat = geoCoding[0].lat
                val lon = geoCoding[0].lon

                val weatherInfo = clientApiRepository.fetchWeatherInfo(lat, lon)
                Timber.d("=Test= complete weatherInfo")
                weatherInfo?.let {
                    _weatherInfoLiveData.postValue(it)
                }
            }
        }
    }

    fun onClickAreaText(view: View) {
        _navigate.value = Event("ToWeatherAreaSetting")
    }

    fun onClickSettingsButton(view: View) {
        _navigate.value = Event("ToSettings")
    }

    fun onClickReloadButton(view: View) {
        _navigate.value = Event("ToReload")
    }

    fun onClickNewsItem(data: NewsHorizontalData) {
        Timber.d("onClickItem url:" + data.url)
        _newsUrl = data.url
        _navigate.value = Event("ToNewsDetail")
    }

    fun getNewsUrl() = _newsUrl

    private fun fetchNewsInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val resultNewsJapan = clientApiRepository.fetchNewsInfo("japan")
            Timber.d("=Test= complete NewsJapan")
            resultNewsJapan?.let {
                _newsJapanLiveData.postValue(it)
                // TODO:リリース前は以下を削除
                _newsEntertainmentLiveData.postValue(it)
                _newsSportsLiveData.postValue(it)
            }

            // TODO:リリース前は以下を実行する
//            val resultNewsEntertainment = clientApiRepository.fetchNewsInfo("entertainment")
//            resultNewsEntertainment?.let {
//                _newsEntertainmentLiveData.postValue(it)
//            }
//
//            val resultNewsSports = clientApiRepository.fetchNewsInfo("sports")
//            resultNewsSports?.let {
//                _newsSportsLiveData.postValue(it)
//            }
        }
    }

    fun fetchWeatherAndNews() {
        fetchWeatherInfo()
        fetchNewsInfo()
    }

    fun resetWeatherAndNews() {
        _weatherInfoLiveData.value = null
        _newsJapanLiveData.value = null
        _newsEntertainmentLiveData.value = null
        _newsSportsLiveData.value = null
    }
}