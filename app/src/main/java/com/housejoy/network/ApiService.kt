package com.housejoy.network

import com.housejoy.model.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/homedata")
    fun getHouseListAsync(): Deferred<Response<ApiResponse>>


}