package com.woodny.weathernewsapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woodny.weathernewsapplication.model.data.WeatherInfoResponse
import com.woodny.weathernewsapplication.model.repository.ClientApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val clientApiRepository: ClientApiRepository
) : ViewModel() {
    private var _weatherInfoLiveData = MutableLiveData<WeatherInfoResponse>()
    val weatherInfoLiveData: LiveData<WeatherInfoResponse> = _weatherInfoLiveData

    fun fetchWeatherInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = clientApiRepository.fetchWeatherInfo("函館市")
            result.let {
                _weatherInfoLiveData.postValue(it)
            }
        }
    }
}