package com.germantv.wapicompose.widgets

import androidx.compose.runtime.Composable
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.germantv.wapicompose.model.Favorite
//import com.germantv.wapicompose.model.Favorite
import com.germantv.wapicompose.navigation.WeatherScreens
import com.germantv.wapicompose.screens.favorite.FavoriteViewModel
import okhttp3.internal.filterList

//import com.germantv.wapicompose.screens.favorites.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    myIcon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    val showIt = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    TopAppBar(
        title = { Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold) },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { /*TODO*/
                    onAddActionClicked.invoke()
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "More Icon"
                    )

                }
            } else {
                Box {}
            }
        },
        navigationIcon = {
            if (myIcon != null) {

                Log.d("AppBar","A")
                Icon(
                    imageVector = myIcon,
                    contentDescription = "Search Icon",
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    }
                )
            }
            if (isMainScreen) {
                //will return list
                val isAlreadyFavList = favoriteViewModel.favList.collectAsState().value.filter {item ->
                    (item.city == title.split(",")[0])
                }
                if(isAlreadyFavList.isNullOrEmpty()){
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite Icon",
                        tint = Color.Red,
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val cityCountry = title.split(",");
                                favoriteViewModel.insertFavorite(
                                    favorite = Favorite(
                                        city = cityCountry[0], cityCountry[1]
                                    )
                                ).run {
                                    showIt.value = true
                                }
                            }
                    )
                }else{
                    showIt.value = false
                    Box{}
                }
                ShowToast(context = context, showIt)

            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Transparent),
    )
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
       if(showIt.value){
            Toast.makeText(context,"Added to Favorite",Toast.LENGTH_SHORT).show()
       }
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember { mutableStateOf(true) }
    val items = listOf("About", "Favorites", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { /*TODO*/ },
            modifier = Modifier
                .width(140.dp)
                .background(
                    Color.White
                )
        ) {

            items.forEachIndexed { index, name ->

                DropdownMenuItem(
                    text = { MenuItem(text = name, navController = navController) },
                    onClick = {
                        expanded = false
                        showDialog.value = false
                    })
            }

        }
    }
}

@Composable
fun MenuItem(text: String, navController: NavController) {
    Row(
        modifier = Modifier.padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = when (text) {
                "About" -> Icons.Default.Info
                "Favorites" -> Icons.Default.FavoriteBorder
                else -> Icons.Default.Settings

            }, contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.padding(end = 5.dp)
        )
        Text(text = text, modifier = Modifier.clickable {
            navController.navigate(
                when (text) {
                    "About" -> WeatherScreens.AboutScreen.name
                    "Favorites" -> WeatherScreens.FavoritesScreen.name
                    else -> WeatherScreens.SettingScreen.name
                }
            )
        })
    }

}



