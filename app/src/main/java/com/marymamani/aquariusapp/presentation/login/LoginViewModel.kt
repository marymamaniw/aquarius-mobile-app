package com.marymamani.aquariusapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marymamani.aquariusapp.domain.model.UserCredentials
import com.marymamani.aquariusapp.domain.model.UserInfo
import com.marymamani.aquariusapp.domain.usecase.LoginUserUseCase
import com.marymamani.aquariusapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow<ResultState<UserInfo>>(ResultState.Inactive())
    val loginState: StateFlow<ResultState<UserInfo>> = _loginState

    fun loginUser(credentials: UserCredentials) {
        viewModelScope.launch {
            _loginState.value = ResultState.Loading()

            val result = loginUserUseCase(credentials)

            if (result.isSuccess) {
                _loginState.value = ResultState.Success(null)
            } else {
                _loginState.value = ResultState.Error(result.exceptionOrNull()?.message ?: "Error")
            }
        }
    }
}