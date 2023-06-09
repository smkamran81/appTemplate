package com.germantv.wapicompose.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.germantv.wapicompose.data.DataOrException
import com.germantv.wapicompose.model.Weather
import com.germantv.wapicompose.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    /*
    val data : MutableState<DataOrException<Weather, Boolean, Exception>>
            = mutableStateOf(DataOrException(null,true,Exception("")))
     */
    //, units: String
    suspend fun getWeatherData(city: String)
            : DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(cityQuery = city)

    }


    /*
    private fun loadWeather(){
        getWeather("karachi")
    }

    init {
        loadWeather()
    }

    private fun getWeather(city: String){

        viewModelScope.launch {
            if(city.isEmpty()){
                 return@launch
            }else{
                data.value.loading = true
                data.value = repository.getWeather(cityQuery = city)
                if(data.value.data.toString().isNotEmpty()){
                      data.value.loading = false
                }
            }
        }
        Log.d("MainViewModel","getWeather: ${data.value.data.toString()}")
    }
    */


}