package com.example.mobileup.view.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileup.data.repository.Repository
import com.example.mobileup.models.InfoCoin
import com.example.mobileup.models.SingleLiveEvent
import kotlinx.coroutines.launch
import retrofit2.Response

class AboutViewModel(private val repository: Repository) : ViewModel() {

    val liveData : SingleLiveEvent<Response<InfoCoin>> = SingleLiveEvent()

    fun getInfoAboutCoin(id : String) {
        viewModelScope.launch {
            val response = repository.getInfoAboutCoin(id)
            liveData.value = response
        }
    }
}