package com.germantv.wapicompose.screens.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.germantv.wapicompose.R
import com.germantv.wapicompose.data.DataOrException
import com.germantv.wapicompose.model.Weather
import com.germantv.wapicompose.model.WeatherItem
import com.germantv.wapicompose.navigation.WeatherScreens
import com.germantv.wapicompose.screens.setting.SettingsViewModel
import com.germantv.wapicompose.utils.formatDate
import com.germantv.wapicompose.utils.formatDateTime
import com.germantv.wapicompose.utils.formatDecimals
import com.germantv.wapicompose.widgets.WeatherAppBar


@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                formatDate(weather.dt)
                    .split(",")[0],
                modifier = Modifier.padding(start = 5.dp)
            )
            WeatherStateImage(imageURL = imageUrl)
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(
                    weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleMedium
                )

            }
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(formatDecimals(weather.temp.max) + "º")

                }
                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray
                    )
                ) {
                    append(formatDecimals(weather.temp.min) + "º")
                }
            })

        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(30.dp, 30.dp)
            )
            Text(text = "${weather.humidity}%")
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(30.dp, 30.dp)
            )
            Text(text = "${weather.pressure} psi")
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                modifier = Modifier.size(30.dp, 30.dp),
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon"
            )
            Text(text = "${weather.humidity} mph")
        }
    }

}

@Composable
fun SunsetSunRiseRow(data: Weather) {
    val weatherItem = data.list[0]

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise image",
                modifier = Modifier.size(25.dp)
            )
            Text(text = formatDateTime(weatherItem.sunrise))
        }
        Row() {
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset image",
                modifier = Modifier.size(25.dp)
            )
            Text(text = formatDateTime(weatherItem.sunset))
        }
    }
    Text(text = "This Week")
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(1.dp),
        color = Color.LightGray,
        shape = RoundedCornerShape(10.dp)

    ) {
        LazyColumn(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)) {
            items(items = data.list) { item: WeatherItem ->
                WeatherDetailRow(weather = item)
            }
        }
    }
}

@Composable
fun WeatherMainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String? = "karachi",
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    val curCity: String = if (city!!.isBlank()) "karachi" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }
    Log.d("XXX", "A $unitFromDb");
    if (!unitFromDb.isNullOrEmpty()) {
        Log.d("XXX", "B");
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        //isImperial = unit == "Fahrenheit"
        if (unit == "imperial") {
            isImperial = true
        }
    }
        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(city = curCity,unit = unit)
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(weather = weatherData.data!!, navController)
        } else {
            Text(text = "Data XX")
        }



    /*
    produceState is a function provided by Jetpack Compose for managing state in a composable function.
    It allows you to create a state that can be observed and updated, similar to mutableStateOf.
    However, produceState is designed to handle more complex state scenarios where the state needs to be derived
    from side effects or asynchronous operations.
     */

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + " ," + weather.city.country,
            navController = navController,
            onAddActionClicked = { navController.navigate(WeatherScreens.SearchScreen.name) },

            ) {
            //trailing lambda
            Log.d("TAG", "Button Clicked")
        }
    }) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            MainContent(data = weather)
        }
    }

}

@Composable
fun MainContent(data: Weather, settingsViewModel: SettingsViewModel = hiltViewModel()) {
    //10d.png
    val imageURL = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = formatDate(data.list[0].dt), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageURL = imageURL)
                Text(
                    text = formatDecimals(data.list[0].temp.day) + "°",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(text = data.list[0].weather[0].main)
            }

        }
        Divider()
        HumidityWindPressureRow(data.list[0])
        Divider()
        SunsetSunRiseRow(data)
    }
}

@Composable
fun WeatherStateImage(imageURL: String) {
    /*
    AsyncImage(
        model = "https://example.com/image.jpg",
        contentDescription = null,
    )
    */
    Image(
        painter = rememberAsyncImagePainter(model = imageURL),
        contentDescription = null,
        modifier = Modifier.size(80.dp)
    )
}
