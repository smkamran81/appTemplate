package com.germantv.wapicompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.germantv.wapicompose.screens.WeatherDummyScreen
import com.germantv.wapicompose.screens.WeatherSplashScreen


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
    }
    //1
}