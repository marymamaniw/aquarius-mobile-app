package com.marymamani.aquariusapp.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.marymamani.aquariusapp.R
import com.marymamani.aquariusapp.domain.model.UserCredentials
import com.marymamani.aquariusapp.navigation.AppScreens
import com.marymamani.aquariusapp.ui.widgets.OutlinedTextFieldCustom
import com.marymamani.aquariusapp.ui.widgets.ProgressLoading
import com.marymamani.aquariusapp.utils.ResultState

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val loginState by viewModel.loginState.collectAsState()

    val context = LocalContext.current

    var stateLoading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = loginState) {
        when (loginState) {
            is ResultState.Success -> {
                stateLoading = false
                navController.popBackStack()
                navController.navigate(AppScreens.Home.route)
            }
            is ResultState.Error -> {
                stateLoading = false
                val errorMessage = (loginState as ResultState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            is ResultState.Loading -> {
                stateLoading = true
            }
            else -> Unit
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()) {

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        ProgressLoading(
            stateLoading
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .background(color = Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_1),
                contentDescription = "",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_2),
                contentDescription = "",
                modifier = Modifier.width(180.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.log_in_descrption),
                color = MaterialTheme.colorScheme.tertiaryContainer
            )
            Spacer(modifier = Modifier.height(100.dp))
            OutlinedTextFieldCustom(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(id = R.string.email)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(id = R.string.email)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                isError = loginState is ResultState.Error
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextFieldCustom(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(id = R.string.password)) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(id = R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                isError = loginState is ResultState.Error
            )
            Spacer(modifier = Modifier.height(70.dp))
            Button(
                onClick = {
                    viewModel.loginUser(UserCredentials(email, password))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                enabled = (email.isNotEmpty() && password.isNotEmpty() && loginState !is ResultState.Loading),
            ) {
                Text(
                    stringResource(id = R.string.log_in)
                )
            }
        }
    }

}

