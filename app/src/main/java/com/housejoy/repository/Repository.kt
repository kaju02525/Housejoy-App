package com.housejoy.repository

import androidx.lifecycle.MutableLiveData
import com.housejoy.App
import com.housejoy.R
import com.housejoy.model.ApiResponse
import com.housejoy.network.ApiStatus.isCheckAPIStatus
import com.housejoy.network.RestClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Repository(private val restClient: RestClient) {
    private val userList = MutableLiveData<ApiResponse>()
    private val errorMess = MutableLiveData<String>()
    private var job: Job? = null


    fun callHouseListApi() {
        job=CoroutineScope(Dispatchers.IO).launch {
            try {
                restClient.webServices().getHouseListAsync().await().let {
                    if (it.isSuccessful)
                        userList.postValue(it.body()!!)
                    else
                        errorMess.value=isCheckAPIStatus(it.code(),it.errorBody())
                }
            } catch (e: Exception) {
                errorMess.postValue(App.appContext?.getString(R.string.no_internet))
                e.printStackTrace()
            }
        }
    }

    fun getSuggestions(): MutableLiveData<ApiResponse> {
        return userList
    }

    fun getErrorMessage(): MutableLiveData<String> {
        return errorMess
    }
    fun onCleared(){
        job?.cancel()
    }
}

