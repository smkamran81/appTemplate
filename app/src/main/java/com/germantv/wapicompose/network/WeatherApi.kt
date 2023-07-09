package com.germantv.wapicompose.network

import com.germantv.wapicompose.model.Weather
import com.germantv.wapicompose.model.WeatherObject
import com.germantv.wapicompose.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    //q=karachi&appid=ed60fcfbd110ee65c7150605ea8aceea&units=metric
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query:String,
        @Query("units") units:String = "imperial",//metric
        @Query("appid") appid:String = Constants.API_KEY) : Weather
}