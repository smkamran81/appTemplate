package com.germantv.wapicompose.repository

import android.provider.ContactsContract.Data
import android.util.Log
import com.germantv.wapicompose.data.DataOrException
import com.germantv.wapicompose.model.Weather
import com.germantv.wapicompose.model.WeatherObject
import com.germantv.wapicompose.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    //WeatherObject
    suspend fun getWeather(
        cityQuery: String,
        units: String
    ): DataOrException<Weather, Boolean, Exception> {

        val response = try {
            api.getWeather(cityQuery, units = units)
        } catch (e: Exception) {
            Log.d("WeatherRepository", "Exception: $e")
            return DataOrException(e = e)
        }
        Log.d("WeatherRepository", "getWeather: $response")

        return DataOrException(data = response)

    }
}