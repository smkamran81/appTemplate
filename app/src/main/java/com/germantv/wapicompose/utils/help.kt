package com.germantv.wapicompose.utils

// Create WeatherApplication.kt file
// dependency 1
// Create di/AppModule.kt file
// Adding entry point in MainActivity
// dependency 2
// Setting up navigation and creating enum class
// navigation/WeatherScreens.kt
// Creating WeatherNavigation class
// screens/WeatherSplashScreen first screen of the app
// Updating main activity to add navigation support and linked to splash screen
// added animation in splashscreen, created new screen MainScreen and navigate to MainScreen

// Create data/model classes from json
// Create constants
// create interface WeatherApi in network for retrofit
// dependency 3 retrofit
// Created methods in retrofit interface
// adding providers in AppModule
// Create fun provideOpenWeatherApi() to make API available to whole app
// Create WeatherRepository to get the from our weather api
// Create wrapper class DataOrException to handle response from the API.
// Create MainViewModel class to connect with Repository
// Now pass MainViewModel dependency to the activity in WeatherNavigation
//Room
// UI => ViewModel => Repository => Dao => Database
//add dependency = > data class for tables = > Dao intterface for methods => abstract class to create database
//Update AppModule to add Dao and database provider
//Create repository with Dao injection
//to create ViewModel class with repository injection