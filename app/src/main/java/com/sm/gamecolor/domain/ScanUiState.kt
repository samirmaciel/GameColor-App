package com.sm.gamecolor.domain

data class ScanUiState(
    val scannedDevices: List<UserGame> = emptyList(),
    val foundedDevices: Boolean = false,
    val errorMessage: String? = null,
    val receivedLine: Line? = null
)
