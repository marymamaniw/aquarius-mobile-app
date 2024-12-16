package com.marymamani.aquariusapp.domain.usecase

import com.marymamani.aquariusapp.domain.model.Area
import com.marymamani.aquariusapp.domain.repository.AreaRepository

class GetAreasUseCase(private val areaRepository: AreaRepository) {

    suspend operator fun invoke(): Result<List<Area>> {
        return areaRepository.getAllAreas()
    }
}