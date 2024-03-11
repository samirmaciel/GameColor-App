package com.sm.gamecolor.view.drawScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sm.gamecolor.bluetooth.BluetoothController
import com.sm.gamecolor.bluetooth.TransferConnectionResult
import com.sm.gamecolor.bluetooth.DeviceConnectionState
import com.sm.gamecolor.domain.ConnectionState
import com.sm.gamecolor.domain.Line
import com.sm.gamecolor.domain.ScanUiState
import com.sm.gamecolor.domain.ScreenSelection
import com.sm.gamecolor.domain.UserGame
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DrawScreenViewModel(val bluetoothController: BluetoothController) : ViewModel() {

    private val _navigationState = MutableStateFlow(ScreenSelection.INITIAL)
    val navigationState = _navigationState.asStateFlow()

    private val _DeviceConnectionState = MutableStateFlow(DeviceConnectionState())
    val deviceConnectionState get() = _DeviceConnectionState.asStateFlow()

    private val _scanUiState = MutableStateFlow(ScanUiState())
    val scanUiState = combine(
        bluetoothController.scannedDevices,
        _scanUiState
    ) { scannedDevices, state ->
        state.copy(
            foundedDevices = scannedDevices.isNotEmpty(),
            scannedDevices = scannedDevices
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _scanUiState.value)

    private var deviceConnectionJob: Job? = null

    init {

        bluetoothController.errors.onEach { error ->
            _scanUiState.update { it.copy(
                errorMessage = error
            ) }
        }.launchIn(viewModelScope)
    }

    fun connectToDevice(userGame: UserGame) {
        _DeviceConnectionState.update { it.copy(connectionState = ConnectionState.CONNECTING) }
        deviceConnectionJob = bluetoothController
            .connectToDevice(userGame)
            .listenTransferConnectionResult()
    }

    fun disconnectFromDevice() {
        deviceConnectionJob?.cancel()
        bluetoothController.closeConnection()
        _DeviceConnectionState.update { it.copy(
            connectionState = null
        ) }
    }

    fun waitForIncomingConnections() {
        _DeviceConnectionState.update { it.copy(connectionState = ConnectionState.CONNECTING) }
        deviceConnectionJob = bluetoothController
            .startBluetoothServer()
            .listenTransferConnectionResult()
    }

    fun sendLine(line: Line) {
        viewModelScope.launch {
            bluetoothController.trySendLine(line)
        }
    }

    fun startScan() {
        bluetoothController.startDiscovery()
    }

    fun stopScan() {
        bluetoothController.stopDiscovery()
    }

    private fun Flow<TransferConnectionResult>.listenTransferConnectionResult(): Job{
        return onEach { result ->
            when(result) {
                TransferConnectionResult.ConnectionEstablished -> {
                    _DeviceConnectionState.update { it.copy(
                        connectionState = ConnectionState.CONNECTED,
                        errorMessage = null
                    ) }
                }
                is TransferConnectionResult.TransferSucceeded -> {
                    _DeviceConnectionState.update { it.copy(
                        receivedLine = result.line
                    ) }
                }
                is TransferConnectionResult.Error -> {
                    _DeviceConnectionState.update { it.copy(
                        connectionState = null,
                        errorMessage = result.message
                    ) }
                }
            }
        }
            .catch { throwable ->
                bluetoothController.closeConnection()
                _DeviceConnectionState.update { it.copy(
                    connectionState = null
                ) }
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        bluetoothController.release()
    }
    fun goToDrawScreen(){
        _navigationState.value = ScreenSelection.DRAW
    }

    fun goToInitialScreen() {
        disconnectFromDevice()
        _navigationState.value = ScreenSelection.INITIAL
    }

    fun goToWaitingScreen(){
        _navigationState.value = ScreenSelection.WAITING
    }

}
