package com.germantv.wapicompose.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.germantv.wapicompose.screens.WeatherDummyScreen
import com.germantv.wapicompose.screens.main.WeatherMainScreen
import com.germantv.wapicompose.screens.WeatherSplashScreen
import com.germantv.wapicompose.screens.about.AboutScreen
import com.germantv.wapicompose.screens.favorite.FavoriteScreen
import com.germantv.wapicompose.screens.main.MainViewModel
import com.germantv.wapicompose.screens.search.SearchScreen
import com.germantv.wapicompose.screens.setting.SettingScreen

@Composable
fun WeatherNavigation() {
    //1
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {

        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }


        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}", arguments = listOf(navArgument(name = "city"){
            type = NavType.StringType
        })) { navBack ->
            //packaging passed data
            navBack.arguments?.getString("city").let { city ->

                val mainViewModel = hiltViewModel<MainViewModel>()
                WeatherMainScreen(navController = navController,mainViewModel,city = city )
            }

        }

        composable(WeatherScreens.SearchScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }
        composable(WeatherScreens.SettingScreen.name) {
            SettingScreen(navController = navController)
        }
        composable(WeatherScreens.FavoritesScreen.name) {
            FavoriteScreen(navController = navController)
        }
    }
    //1
}