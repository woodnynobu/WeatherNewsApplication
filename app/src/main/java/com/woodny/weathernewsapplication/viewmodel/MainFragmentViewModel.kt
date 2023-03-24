package com.woodny.weathernewsapplication.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woodny.weathernewsapplication.event.Event
import com.woodny.weathernewsapplication.model.data.NewsResponse
import com.woodny.weathernewsapplication.model.data.WeatherInfoResponse
import com.woodny.weathernewsapplication.model.repository.ClientApiRepository
import com.woodny.weathernewsapplication.model.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val clientApiRepository: ClientApiRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private var _weatherInfoLiveData = MutableLiveData<WeatherInfoResponse>()
    val weatherInfoLiveData: LiveData<WeatherInfoResponse> = _weatherInfoLiveData

    private val _navigate = MutableLiveData<Event<String>>()
    val navigate : LiveData<Event<String>>
        get() = _navigate

    private val _area = MutableLiveData<String>()
    val area: LiveData<String> = _area

    private var _newsJapanLiveData = MutableLiveData<NewsResponse>()
    val newsJapanLiveData: LiveData<NewsResponse> = _newsJapanLiveData

    private var _newsEntertainmentLiveData = MutableLiveData<NewsResponse>()
    val newsEntertainmentLiveData: LiveData<NewsResponse> = _newsEntertainmentLiveData

    private var _newsSportsLiveData = MutableLiveData<NewsResponse>()
    val newsSportsLiveData: LiveData<NewsResponse> = _newsSportsLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val resultNewsJapan = clientApiRepository.fetchNewsInfo("japan")
            resultNewsJapan?.let {
                _newsJapanLiveData.postValue(it)
            }

            val resultNewsEntertainment = clientApiRepository.fetchNewsInfo("entertainment")
            resultNewsEntertainment?.let {
                _newsEntertainmentLiveData.postValue(it)
            }

            val resultNewsSports = clientApiRepository.fetchNewsInfo("sports")
            resultNewsSports?.let {
                _newsSportsLiveData.postValue(it)
            }
        }
    }

    fun fetchWeatherInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val city = dataStoreRepository.loadCity().first()
            _area.postValue(city)

            val geoCodingInfo = clientApiRepository.fetchGeoCodingInfo(city)
            geoCodingInfo?.let {geoCoding ->
                // 先頭番地のみ取得
                val lat = geoCoding[0].lat
                val lon = geoCoding[0].lon

                val weatherInfo = clientApiRepository.fetchWeatherInfo(lat, lon)
                weatherInfo?.let {
                    _weatherInfoLiveData.postValue(it)
                }
            }
        }
    }

    fun clickAreaText(view: View) {
        _navigate.value = Event("ToWeatherAreaSetting")
    }



    fun fetchNewsInfo(category: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = clientApiRepository.fetchNewsInfo(category, "jp")
//            result.let {
//                _newsLiveData.postValue(it)
//            }
//        }
    }
}