package com.woodny.weathernewsapplication.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woodny.weathernewsapplication.event.Event
import com.woodny.weathernewsapplication.model.repository.ClientApiRepository
import com.woodny.weathernewsapplication.model.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherAreaSettingFragmentViewModel @Inject constructor(
    private val clientApiRepository: ClientApiRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _navigate = MutableLiveData<Event<String>>()
    val navigate: LiveData<Event<String>>
        get() = _navigate

//    private val _editText = MutableLiveData<String>()
//    val editText: LiveData<String>
//        get() = _editText

    // inputTextは双方向データバインディングに設定している
    val inputText = MutableLiveData<String>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val city = dataStoreRepository.loadCity()
            inputText.postValue(city.first())
        }
    }

    fun onClickDecide(view: View) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveCity(inputText.value.toString())
        }
        _navigate.value = Event("ToMain")
    }

}