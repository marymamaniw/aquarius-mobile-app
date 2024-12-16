package com.marymamani.aquariusapp.presentation.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.marymamani.aquariusapp.R
import com.marymamani.aquariusapp.navigation.AppScreens
import com.marymamani.aquariusapp.ui.widgets.ProgressLoading
import com.marymamani.aquariusapp.utils.ResultState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val employeeState by viewModel.employeesState.collectAsState()
    val logOutState by viewModel.logOutState.collectAsState()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    viewModel.fetchEmployees()
                }
            }
        )
    }

    var stateLoading by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = employeeState) {
        when(employeeState) {
            is ResultState.Loading -> {
                stateLoading = true
            } else -> {
                stateLoading = false
            }
        }
    }

    LaunchedEffect(key1 = logOutState) {
        when(logOutState) {
            is ResultState.Success -> {
                navController.popBackStack()
                navController.navigate(AppScreens.Login.route)
            }
            is ResultState.Loading -> {
                stateLoading = true
            } else -> {
                stateLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                LargeTopAppBar(
                    title = {
                        Column(
                            modifier = Modifier.padding(
                                bottom = if (scrollBehavior.state.collapsedFraction == 0f) 16.dp else 0.dp,
                                end = 16.dp
                            )
                        ) {
                            if (scrollBehavior.state.collapsedFraction == 0f) {
                                Row {
                                    Text(
                                        text = "Hola Mary",
                                        style = TextStyle(
                                            fontWeight = FontWeight(300),
                                            fontSize = 20.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.weight(1f))

                                    val infiniteTransition = rememberInfiniteTransition()
                                    val rotation by infiniteTransition.animateFloat(
                                        initialValue = 0f,
                                        targetValue = 360f,
                                        animationSpec = infiniteRepeatable(
                                            animation = tween(durationMillis = 600, easing = LinearEasing),
                                            repeatMode = RepeatMode.Restart
                                        ), label = ""
                                    )
                                    Icon(
                                        modifier = Modifier
                                            .padding(end = 16.dp)
                                            .clickable {
                                            viewModel.fetchEmployees()
                                        }.graphicsLayer {
                                            rotationZ = if (stateLoading) rotation else 0f
                                        },
                                        imageVector = Icons.Outlined.Refresh,
                                        contentDescription = "Sync",

                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = stringResource(id = R.string.employees))
                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    onClick = {
                                        navController.navigate(
                                            AppScreens.EditEmployee.route
                                                .replace("{idEmployee}", "")
                                        )
                                    },
                                    modifier = Modifier.size(50.dp),
                                    shape = RoundedCornerShape(20.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (scrollBehavior.state.collapsedFraction == 0f) MaterialTheme.colorScheme.background.copy(
                                            alpha = 0.4f
                                        ) else MaterialTheme.colorScheme.primary,
                                    ),
                                    elevation = ButtonDefaults.elevatedButtonElevation(
                                        defaultElevation = 0.dp
                                    ),
                                    border = BorderStroke(
                                        0.5.dp,
                                        if (scrollBehavior.state.collapsedFraction == 0f) MaterialTheme.colorScheme.background.copy(
                                            alpha = 0.4f
                                        ) else MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Person,
                                        contentDescription = "Add"
                                    )
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                Button(
                                    onClick = {
                                        viewModel.logOut()
                                    },
                                    modifier = Modifier.size(50.dp),
                                    shape = RoundedCornerShape(20.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (scrollBehavior.state.collapsedFraction == 0f) MaterialTheme.colorScheme.background.copy(
                                            alpha = 0.4f
                                        ) else MaterialTheme.colorScheme.primary,
                                    ),
                                    elevation = ButtonDefaults.elevatedButtonElevation(
                                        defaultElevation = 0.dp
                                    ),
                                    border = BorderStroke(
                                        0.5.dp,
                                        if (scrollBehavior.state.collapsedFraction == 0f) MaterialTheme.colorScheme.background.copy(
                                            alpha = 0.4f
                                        ) else MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.ExitToApp,
                                        contentDescription = "LogOut"
                                    )
                                }
                            }
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White
                    )
                )
                if (scrollBehavior.state.collapsedFraction == 0f) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                        shape = RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        ),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                    ) {}
                }
            }
        }
    ) { innerPadding ->
        if (stateLoading) {
            ProgressLoading(
                stateLoading,
                Color.Transparent
            )
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState()),
        ) {
            if (employeeState is ResultState.Success) {
                employeeState.data?.let {
                    it.forEach { employee ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(1.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = employee.name,
                                                style = TextStyle(
                                                    fontWeight = FontWeight(250),
                                                    fontSize = 18.sp
                                                )
                                            )
                                            Text(
                                                text = stringResource(id = R.string.name),
                                                style = TextStyle(
                                                    fontWeight = FontWeight(400)
                                                )
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Row {
                                    Column {
                                        Text(
                                            text = employee.email,
                                            style = TextStyle(
                                                fontWeight = FontWeight(250),
                                                fontSize = 18.sp
                                            )
                                        )
                                        Text(
                                            text = stringResource(id = R.string.email),
                                            style = TextStyle(
                                                fontWeight = FontWeight(400)
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Column {
                                        Text(
                                            text = employee.phoneNumber,
                                            style = TextStyle(
                                                fontWeight = FontWeight(250),
                                                fontSize = 18.sp
                                            )
                                        )
                                        Text(
                                            text = stringResource(id = R.string.number_phone),
                                            style = TextStyle(
                                                fontWeight = FontWeight(400)
                                            )
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.primary.copy(
                                                0.8f
                                            )
                                        ),
                                        shape = RoundedCornerShape(16.dp),
                                        border = BorderStroke(
                                            1.dp,
                                            MaterialTheme.colorScheme.primary.copy(0.8f)
                                        )
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(10.dp),
                                            text = employee.area?.name ?: "",
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Button(
                                        onClick = {
                                            viewModel.deleteEmployee(employee.code, employee.id)
                                        },
                                        modifier = Modifier.size(30.dp),
                                        contentPadding = PaddingValues(0.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Transparent
                                        ),
                                        elevation = ButtonDefaults.elevatedButtonElevation(
                                            defaultElevation = 0.dp
                                        ),
                                        border = BorderStroke(0.5.dp, Color.Transparent)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Button(
                                        onClick = {
                                            navController.navigate(
                                                AppScreens.EditEmployee.route
                                                    .replace("{idEmployee}", employee.id.toString())
                                            )
                                        },
                                        modifier = Modifier.size(30.dp),
                                        contentPadding = PaddingValues(0.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Transparent
                                        ),
                                        elevation = ButtonDefaults.elevatedButtonElevation(
                                            defaultElevation = 0.dp
                                        ),
                                        border = BorderStroke(0.5.dp, Color.Transparent)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Edit,
                                            contentDescription = "Edit",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}