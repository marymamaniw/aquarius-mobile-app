package com.marymamani.aquariusapp.data.mappers

import com.marymamani.aquariusapp.data.datasource.local.db.AreaEntity
import com.marymamani.aquariusapp.data.datasource.remote.models.AreaResponse
import com.marymamani.aquariusapp.domain.model.Area

fun AreaResponse.toArea(): Area {
    return Area(
        code = this.code,
        name = this.name
    )
}

fun AreaResponse.toAreaEntity(): AreaEntity {
    return AreaEntity(
        code = this.code,
        name = this.name
    )
}

fun AreaEntity.toArea(): Area {
    return Area(
        code = this.code ?: "",
        name = this.name ?: ""
    )
}