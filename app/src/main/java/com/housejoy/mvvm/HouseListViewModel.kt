package com.housejoy.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.housejoy.model.ApiResponse
import com.housejoy.repository.Repository

class HouseListViewModel(private val repository: Repository) : ViewModel() {



    fun callHouseListApi() {
        return repository.callHouseListApi()
    }

    fun getNewsData(): MutableLiveData<ApiResponse> {
        return repository.getSuggestions()
    }


    fun getErrorMessage(): MutableLiveData<String> {
        return repository.getErrorMessage()
    }

    override fun onCleared() {
        super.onCleared()
        repository.onCleared()
    }
}
