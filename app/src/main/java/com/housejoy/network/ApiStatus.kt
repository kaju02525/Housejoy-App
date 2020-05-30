package com.housejoy.network



import okhttp3.ResponseBody
import org.json.JSONObject

object ApiStatus{

    fun isCheckAPIStatus(statusCode:Int,errorBody: ResponseBody?):String{
        val jsonObject = JSONObject(errorBody!!.string())
        val message = jsonObject.getString("message")
        return when (statusCode) {
            400 -> {
                message
            }
            401 -> {
                message
            }
            500 -> {
                message
            }
            else -> message
        }

    }
}