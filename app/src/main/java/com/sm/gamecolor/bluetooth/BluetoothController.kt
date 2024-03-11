package com.sm.gamecolor.bluetooth

import com.sm.gamecolor.domain.Line
import com.sm.gamecolor.domain.UserGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val isConnected: StateFlow<Boolean>
    val scannedDevices: StateFlow<List<UserGame>>
    val errors: SharedFlow<String>

    fun startDiscovery()
    fun stopDiscovery()

    fun startBluetoothServer(): Flow<TransferConnectionResult>
    fun connectToDevice(device: UserGame): Flow<TransferConnectionResult>

    suspend fun trySendLine(line: Line): Line?

    fun closeConnection()
    fun release()
}