package com.germantv.wapicompose.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.germantv.wapicompose.screens.WeatherDummyScreen
import com.germantv.wapicompose.screens.main.WeatherMainScreen
import com.germantv.wapicompose.screens.WeatherSplashScreen
import com.germantv.wapicompose.screens.main.MainViewModel

@Composable
fun WeatherNavigation() {
    //1
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {

        composable(WeatherScreens.SplashScreen.name) {
            //adding composable screen here
            WeatherSplashScreen(navController = navController)
        }

        composable(WeatherScreens.DummyScreen.name) {
            WeatherDummyScreen(navController = navController)
        }
        composable(WeatherScreens.MainScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            WeatherMainScreen(navController = navController,mainViewModel)
        }
    }
    //1
}