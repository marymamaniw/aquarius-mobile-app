package com.marymamani.aquariusapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.marymamani.aquariusapp.R
import com.marymamani.aquariusapp.navigation.AppScreens
import com.marymamani.aquariusapp.ui.widgets.ProgressLoading

@Composable
fun SplashScreen(navController: NavHostController) {
    val splashViewModel: SplashViewModel = hiltViewModel()
    val userState by splashViewModel.userState.collectAsState()

    when (userState) {
        UserState.Loading -> Splash()
        UserState.Authenticated -> navController.navigate(AppScreens.Home.route) {
            popUpTo(AppScreens.Splash.route) { inclusive = true }
        }
        UserState.Unauthenticated -> navController.navigate(AppScreens.Login.route) {
            popUpTo(AppScreens.Splash.route) { inclusive = true }
        }
    }
}

@Composable
fun Splash() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_1),
            contentDescription = "Logo Aquarius",
            modifier = Modifier.size(200.dp, 200.dp)
        )
    }
}
