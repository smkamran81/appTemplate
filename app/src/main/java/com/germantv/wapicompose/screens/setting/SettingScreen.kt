package com.germantv.wapicompose.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.germantv.wapicompose.model.Unit
import com.germantv.wapicompose.widgets.ShowToast
import com.germantv.wapicompose.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    var unitToggleState by remember {
        mutableStateOf(false) // initially checked
    }
    val measurementUnits = listOf("Metric (C)", "Imperial (F)")
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value

    val defaultChoice = if (choiceFromDb.isNullOrEmpty()) measurementUnits[0]
    else choiceFromDb[0].unit

    var choiceState by remember { mutableStateOf(defaultChoice) }

    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            myIcon = Icons.Default.ArrowBack,
            isMainScreen = false,
            navController = navController
        )
    }, modifier = Modifier.padding(2.dp)) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Change Units of Measurement",
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                    IconToggleButton(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .clip(shape = RectangleShape)
                            .padding(5.dp)
                            .background(
                                Color.Magenta.copy(alpha = 0.4f)
                            ),
                        checked = unitToggleState,
                        onCheckedChange = {
                            unitToggleState = it
                            if (unitToggleState) {
                                choiceState = "Imperial (F)"
                            } else {
                                choiceState = "Metric (C)"
                            }
                        }) {
                        Text(
                            text = if (unitToggleState) {
                                "Imperial (F)"
                            } else {
                                "Metric (C)"
                            }
                        )
                    }
                    Button(
                        onClick = {
                            settingsViewModel.deleteAllUnits()
                            settingsViewModel.insertUnit(unit = Unit(unit = choiceState)).run { 
                                //ShowToast(context = cont, showIt = )
                            }
                        },
                        modifier = Modifier
                            .padding(3.dp)
                            .align(CenterHorizontally),
                        shape = RoundedCornerShape(34.dp),
                        colors = ButtonDefaults.buttonColors(contentColor = Color.Green)
                    ) {
                         Text(text = "Save")
                    }
                }
            }
        }
    }
}