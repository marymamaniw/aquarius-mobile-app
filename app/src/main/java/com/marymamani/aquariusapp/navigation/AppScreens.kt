package com.marymamani.aquariusapp.navigation

sealed class AppScreens(val route: String) {
    data object Splash: AppScreens("splash_screen")
    data object Login: AppScreens("login_screen")
    data object Home: AppScreens("home_screen")
    data object EditEmployee: AppScreens("edit_employee_screen/{idEmployee}")
}