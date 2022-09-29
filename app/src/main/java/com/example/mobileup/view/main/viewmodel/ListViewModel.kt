package com.example.mobileup.view.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileup.data.repository.Repository
import com.google.gson.JsonArray
import kotlinx.coroutines.launch
import retrofit2.Response

class ListViewModel(private val repository: Repository): ViewModel() {

    var listLiveData : MutableLiveData<Response<JsonArray>> = MutableLiveData()

    fun getAllCoin(vs_currency : String, per_page : Int) {
        viewModelScope.launch {
            val response = repository.getAllCoin(vs_currency, per_page)
            listLiveData.value = response
        }
    }
}