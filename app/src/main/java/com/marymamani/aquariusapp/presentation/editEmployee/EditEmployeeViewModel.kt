package com.marymamani.aquariusapp.presentation.editEmployee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marymamani.aquariusapp.domain.model.Area
import com.marymamani.aquariusapp.domain.model.Employee
import com.marymamani.aquariusapp.domain.model.EmployeeForm
import com.marymamani.aquariusapp.domain.usecase.CreateEmployeeUseCase
import com.marymamani.aquariusapp.domain.usecase.GetAreasUseCase
import com.marymamani.aquariusapp.domain.usecase.GetEmployeeByIdUseCase
import com.marymamani.aquariusapp.domain.usecase.UpdateEmployeeUseCase
import com.marymamani.aquariusapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEmployeeViewModel  @Inject constructor(
    private val getAreasUseCase: GetAreasUseCase,
    private val createEmployeeUseCase: CreateEmployeeUseCase,
    private val updateEmployeeUseCase: UpdateEmployeeUseCase,
    private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase
) : ViewModel() {

    private val _areasState = MutableStateFlow<List<Area>>(emptyList())
    val areasState: StateFlow<List<Area>> = _areasState

    private val _createEmployeeState = MutableStateFlow<ResultState<Unit>>(ResultState.Inactive())
    val createEmployeeState: StateFlow<ResultState<Unit>> = _createEmployeeState

    private val _updateEmployeeState = MutableStateFlow<ResultState<Unit>>(ResultState.Inactive())
    val updateEmployeeState: StateFlow<ResultState<Unit>> = _updateEmployeeState

    private val _getEmployeeState = MutableStateFlow<Employee?>(null)
    val getEmployee: StateFlow<Employee?> = _getEmployeeState

    init {
        fetchAreas()
    }

    private fun fetchAreas() {
        viewModelScope.launch {
            val result = getAreasUseCase()
            _areasState.value = when {
                result.isSuccess -> result.getOrNull() ?: emptyList()
                result.isFailure -> emptyList()
                else -> emptyList()
            }
        }
    }

    fun createEmployee(employeeForm: EmployeeForm) {
        viewModelScope.launch {
            _createEmployeeState.value = ResultState.Loading()
            val result = createEmployeeUseCase(employeeForm)
            if (result.isSuccess) {
                _createEmployeeState.value = ResultState.Success(Unit)
            } else {
                _createEmployeeState.value = ResultState.Error(result.exceptionOrNull()?.message ?: "Error")
            }
        }
    }

    fun updateEmployee(employeeForm: EmployeeForm, employeeId: Int) {
        viewModelScope.launch {
            _updateEmployeeState.value = ResultState.Loading()
            val result = updateEmployeeUseCase(getEmployee.value?.code ?: "", employeeForm, employeeId)
            if (result.isSuccess) {
                _updateEmployeeState.value = ResultState.Success(Unit)
            } else {
                _updateEmployeeState.value = ResultState.Error(result.exceptionOrNull()?.message ?: "Error")
            }
        }
    }

    fun getEmployee(id: String) {
        viewModelScope.launch {
            _getEmployeeState.value = getEmployeeByIdUseCase(id.toInt()).getOrNull()
        }
    }
}