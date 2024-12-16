package com.marymamani.aquariusapp.presentation.editEmployee

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.marymamani.aquariusapp.R
import com.marymamani.aquariusapp.domain.model.EmployeeForm
import com.marymamani.aquariusapp.ui.widgets.OutlinedTextFieldCustom
import com.marymamani.aquariusapp.ui.widgets.ProgressLoading
import com.marymamani.aquariusapp.utils.ResultState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEmployeeScreen(
    navController: NavHostController,
    idEmployee: String = ""
) {
    val viewModel: EditEmployeeViewModel = hiltViewModel()

    val areasState by viewModel.areasState.collectAsState()
    val createEmployeeState by viewModel.createEmployeeState.collectAsState()
    val getEmployee by viewModel.getEmployee.collectAsState()
    val updateEmployeeState by viewModel.updateEmployeeState.collectAsState()

    val context = LocalContext.current

    var stateLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (idEmployee.isNotEmpty()) viewModel.getEmployee(idEmployee)
    }

    LaunchedEffect(key1 = createEmployeeState) {
        when(createEmployeeState) {
            is ResultState.Success ->  {
                stateLoading = false

                navController.popBackStack()
            }
            is ResultState.Error -> {
                stateLoading = false
                val errorMessage = (createEmployeeState as ResultState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            is ResultState.Loading -> {
                stateLoading = true
            }
            else -> Unit
        }
    }

    LaunchedEffect(key1 = updateEmployeeState) {
        when(updateEmployeeState) {
            is ResultState.Success ->  {
                stateLoading = false
                navController.popBackStack()
            }
            is ResultState.Error -> {
                stateLoading = false
                val errorMessage = (updateEmployeeState as ResultState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            is ResultState.Loading -> {
                stateLoading = true
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                        text = stringResource(id = if (idEmployee.isNotEmpty()) R.string.edit_employee else R.string.create_employee),
                        style = TextStyle(
                            fontWeight = FontWeight(400),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 20.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        },
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        var name by remember { mutableStateOf( getEmployee?.name ?: "" ) }
        var area by remember { mutableStateOf(getEmployee?.area?.name ?: "") }
        var areaCode by remember { mutableStateOf(getEmployee?.area?.code ?: "") }
        var email by remember { mutableStateOf(getEmployee?.email ?: "") }
        var phoneNumber by remember { mutableStateOf(getEmployee?.phoneNumber ?: "") }

        LaunchedEffect(getEmployee) {
            name = getEmployee?.name ?: ""
            area = getEmployee?.area?.name ?: ""
            areaCode = getEmployee?.area?.code ?: ""
            email = getEmployee?.email ?: ""
            phoneNumber = getEmployee?.phoneNumber ?: ""
        }

        Box( modifier = Modifier
            .fillMaxSize() ) {
            ProgressLoading(
                stateLoading
            )
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                val outlineColors = OutlinedTextFieldDefaults.colors(
                    focusedPlaceholderColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.primary.copy(0.6f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f),
                    cursorColor = MaterialTheme.colorScheme.outline,
                    unfocusedTextColor = MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f),
                    focusedTextColor = MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f),
                    unfocusedLabelColor = MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f),
                    focusedLabelColor = MaterialTheme.colorScheme.primary.copy(0.6f),
                )

                OutlinedTextFieldCustom(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(id = R.string.name)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(id = R.string.name)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    colors = outlineColors
                )
                Spacer(modifier = Modifier.height(20.dp))

                var expanded by remember { mutableStateOf(false) }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .wrapContentSize(Alignment.TopStart)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f),
                        shape = RoundedCornerShape(25.dp)
                    ),
                ) {
                    Row(
                        modifier = Modifier
                            .clickable {
                                expanded = true
                            }
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = area.ifEmpty { stringResource(id = R.string.select_area) },
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f),
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Localized description",
                                tint = MaterialTheme.colorScheme.tertiaryContainer.copy(0.5f)
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        areasState.forEach {
                            DropdownMenuItem(
                                text = { Text(it.name) },
                                onClick = {
                                    area = it.name
                                    areaCode = it.code
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextFieldCustom(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(id = R.string.email)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(id = R.string.email)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    colors = outlineColors
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextFieldCustom(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text(stringResource(id = R.string.number_phone)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(id = R.string.number_phone)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    colors = outlineColors
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        if (idEmployee.isNotEmpty()) {
                            viewModel.updateEmployee(
                                EmployeeForm(
                                    areaCode,
                                    name,
                                    email,
                                    phoneNumber,
                                    getEmployee?.code ?: ""
                                ),
                                employeeId = idEmployee.toInt()
                            )
                        } else {
                            viewModel.createEmployee(
                                EmployeeForm(
                                    areaCode,
                                    name,
                                    email,
                                    phoneNumber
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    enabled = name.isNotEmpty() &&
                            area.isNotEmpty() &&
                            email.isNotEmpty() &&
                            phoneNumber.isNotEmpty(),
                ) {
                    Text(
                        stringResource(id = R.string.save)
                    )
                }
            }
        }
    }
}