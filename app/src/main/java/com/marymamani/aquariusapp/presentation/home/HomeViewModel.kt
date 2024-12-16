package com.marymamani.aquariusapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marymamani.aquariusapp.domain.model.Employee
import com.marymamani.aquariusapp.domain.usecase.DeleteEmployeeUseCase
import com.marymamani.aquariusapp.domain.usecase.GetAreasUseCase
import com.marymamani.aquariusapp.domain.usecase.GetEmployeesUseCase
import com.marymamani.aquariusapp.domain.usecase.LogOutUseCase
import com.marymamani.aquariusapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllEmployeesUseCase: GetEmployeesUseCase,
    private val deleteEmployeeUseCase: DeleteEmployeeUseCase,
    private val getAreasUseCase: GetAreasUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {
    private val _employeesState = MutableStateFlow<ResultState<List<Employee>>>(ResultState.Loading())
    val employeesState: StateFlow<ResultState<List<Employee>>> = _employeesState

    private val _logOutState = MutableStateFlow<ResultState<Unit>>(ResultState.Inactive())
    val logOutState: StateFlow<ResultState<Unit>> = _logOutState

    init {
        fetchAreas()
        fetchEmployees()
    }

    fun fetchEmployees() {
        viewModelScope.launch {
            _employeesState.value = ResultState.Loading()
            val result = getAllEmployeesUseCase()
            _employeesState.value = when {
                result.isSuccess -> ResultState.Success(result.getOrNull() ?: emptyList())
                result.isFailure -> ResultState.Error(result.exceptionOrNull()?.message ?: "Error")
                else -> ResultState.Inactive()
            }
        }
    }

    private fun fetchAreas() {
        viewModelScope.launch {
            getAreasUseCase()
        }
    }

    fun deleteEmployee(employeeCode: String, employeeId: Int) {
        val currentList = _employeesState.value.data?.toMutableList()
        viewModelScope.launch {
            val result = deleteEmployeeUseCase(employeeCode, employeeId)
            if (result.isSuccess) {
                if (_employeesState.value is ResultState.Success) {
                    val updateList = currentList?.filter { employee -> employee.id != employeeId}
                    _employeesState.value = ResultState.Success(updateList)
                }
            } else {
                _employeesState.value = ResultState.Success(currentList)
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            val logOutResult = logOutUseCase()
            if (logOutResult.isSuccess) {
                _logOutState.value = ResultState.Success(Unit)
            } else {
                _logOutState.value = ResultState.Error("Error")
            }
        }
    }
}