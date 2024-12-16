package com.marymamani.aquariusapp.data.mappers

import com.marymamani.aquariusapp.data.datasource.local.db.EmployeeEntity
import com.marymamani.aquariusapp.data.datasource.remote.models.EmployeeRequest
import com.marymamani.aquariusapp.data.datasource.remote.models.EmployeeResponse
import com.marymamani.aquariusapp.domain.model.Area
import com.marymamani.aquariusapp.domain.model.Employee
import com.marymamani.aquariusapp.domain.model.EmployeeForm

fun EmployeeResponse.toEmployee(): Employee {
    return Employee(
        id = 0,
        code = this.code,
         name = this.name,
         area = this.area.toArea(),
         email = this.email,
         phoneNumber = this.phoneNumber,
    )
}

fun EmployeeForm.toEmployeeRequest(): EmployeeRequest {
    return EmployeeRequest(
        areaCode =  this.areaCode,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
    )
}

fun EmployeeResponse.toEmployeeEntity(id: Int) : EmployeeEntity {
    return EmployeeEntity(
        id = id,
        code = this.code,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        areaCode = area.code,
        sync = 0
    )
}

fun EmployeeEntity.toEmployee(area: Area?) : Employee {
    return Employee(
        id = this.id,
        code = this.code ?: "",
        name = this.name ?: "",
        email = this.email ?: "",
        phoneNumber = this.phoneNumber ?: "",
        area = area,
    )
}

fun EmployeeForm.toEmployeeEntity(): EmployeeEntity {
    return EmployeeEntity(
        areaCode =  this.areaCode,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        sync = 1,
        code = ""
    )
}

fun EmployeeEntity.toEmployeeForm() : EmployeeForm {
    return EmployeeForm(
        name = this.name ?: "",
        email = this.email ?: "",
        phoneNumber = this.phoneNumber ?: "",
        areaCode = this.areaCode ?: ""
    )
}