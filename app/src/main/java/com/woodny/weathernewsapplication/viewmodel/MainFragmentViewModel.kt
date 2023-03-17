package com.woodny.weathernewsapplication.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woodny.weathernewsapplication.event.Event
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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val newsGeneral = clientApiRepository.fetchNewsInfo("general", "jp")
            val newsBusiness = clientApiRepository.fetchNewsInfo("business", "jp")
            val newsEntertainment = clientApiRepository.fetchNewsInfo("entertainment", "jp")
            val newsSports = clientApiRepository.fetchNewsInfo("sports", "jp")
            val newsTechnology = clientApiRepository.fetchNewsInfo("technology", "jp")
        }
    }

    fun fetchWeatherInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val city = dataStoreRepository.loadCity().first()
            _area.postValue(city)

            val result = clientApiRepository.fetchWeatherInfo(city)
            result.let {
                _weatherInfoLiveData.postValue(it)
            }
        }
    }

    fun clickAreaText(view: View) {
        _navigate.value = Event("ToWeatherAreaSetting")
    }
}