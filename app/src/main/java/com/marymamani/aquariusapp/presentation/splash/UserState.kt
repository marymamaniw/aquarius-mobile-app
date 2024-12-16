package com.marymamani.aquariusapp.presentation.splash

sealed class UserState {
    object Loading : UserState()
    object Authenticated : UserState()
    object Unauthenticated : UserState()
}
