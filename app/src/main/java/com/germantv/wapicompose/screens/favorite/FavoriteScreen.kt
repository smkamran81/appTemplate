package com.germantv.wapicompose.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.germantv.wapicompose.model.Favorite
import com.germantv.wapicompose.navigation.WeatherScreens
import com.germantv.wapicompose.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            title = "Favorite",
            isMainScreen = false,
            myIcon = Icons.Default.ArrowBack
        ) {
            navController.popBackStack()
        }
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Surface(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val list = favoriteViewModel.favList.collectAsState().value

                    LazyColumn {
                        items(items = list) {
                            CityRow(
                                it,
                                navController = navController,
                                favoriteViewModel = favoriteViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel
) {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                    navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
            },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.LightGray
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(text = favorite.city)
            Surface(modifier = Modifier.padding(5.dp), shape = CircleShape, color = Color.White) {
                Text(text = favorite.country)
            }
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Button",
                tint = Color.Red.copy(0.3f),
                modifier = Modifier.clickable {
                    favoriteViewModel.deleteFavorite(favorite = favorite)
                })
        }
    }
}
