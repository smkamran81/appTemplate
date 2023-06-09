package com.germantv.wapicompose.screens.main

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.germantv.wapicompose.data.DataOrException
import com.germantv.wapicompose.model.Weather

@Composable
fun WeatherMainScreen(navController: NavController,mainViewModel: MainViewModel = hiltViewModel()){

    ShowData(mainViewModel)
}

@Composable
fun ShowData(mainViewModel: MainViewModel){

     val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(initialValue = DataOrException(loading=true)){
         value = mainViewModel.getWeatherData(city = "karachi")
     }.value

     if(weatherData.loading==true){
           CircularProgressIndicator()
     }else if(weatherData.data!=null){
            Text(text = "Data loaded: ${weatherData.data!!.city.country}")
     }else{
         Text(text = "Data XX")
     }
}