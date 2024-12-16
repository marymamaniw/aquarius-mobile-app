package com.marymamani.aquariusapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marymamani.aquariusapp.presentation.editEmployee.EditEmployeeScreen
import com.marymamani.aquariusapp.presentation.home.HomeScreen
import com.marymamani.aquariusapp.presentation.login.LoginScreen
import com.marymamani.aquariusapp.presentation.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.Splash.route
    ) {
        composable(AppScreens.Splash.route) {
            SplashScreen(navController)
        }
        composable(AppScreens.Login.route) {
            LoginScreen(navController)
        }
        composable(AppScreens.Home.route) {
            HomeScreen(navController)
        }
        composable(AppScreens.EditEmployee.route) { backStackEntry ->
            val isEditingEmployee = backStackEntry.arguments?.getString("idEmployee")?: ""
            EditEmployeeScreen(navController, isEditingEmployee)
        }
    }
}