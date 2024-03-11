package com.sm.gamecolor.view.drawScreen.initialScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sm.gamecolor.domain.ConnectionState
import com.sm.gamecolor.view.drawScreen.components.BluetoothUserGameList
import com.sm.gamecolor.view.drawScreen.components.CustomBottomSheet
import com.sm.gamecolor.view.drawScreen.viewModel.DrawScreenViewModel


@Composable
fun InitialScreen(viewModel: DrawScreenViewModel) {

    val scanState by viewModel.scanUiState.collectAsState()
    val deviceConnectionState by viewModel.deviceConnectionState.collectAsState()

    when(deviceConnectionState.connectionState){
        ConnectionState.CONNECTED -> {
            viewModel.goToDrawScreen()
        }

        ConnectionState.CONNECTING -> {
            viewModel.goToWaitingScreen()
        }

        else -> {}
    }

    var openBluetoothBottomSheet by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
                onClick = { viewModel.goToDrawScreen() }) {
                Text(text = "Pintar")
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp), onClick = {
                openBluetoothBottomSheet = !openBluetoothBottomSheet
                viewModel.startScan()
            }) {
                Text(text = "Pintar com amigo(a)")
            }

        }

        CustomBottomSheet(
            height = 300.dp,
            isExpanded = openBluetoothBottomSheet,
            onDismissed = { }) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when {
                    !scanState.foundedDevices -> {
                        viewModel.startScan()

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    scanState.scannedDevices.isNotEmpty() -> {
                        BluetoothUserGameList(
                            modifier = Modifier.weight(1f),
                            userGameList = scanState.scannedDevices,
                            onClick = {
                                viewModel.connectToDevice(it)
                            })
                    }
                }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Start) {
                            Button(
                                modifier = Modifier.padding(10.dp),
                                onClick = { viewModel.waitForIncomingConnections() }) {
                                Text(text = "Aguardar conexão")
                            }
                        }

                        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End)  {
                            Button(
                                modifier = Modifier.padding(10.dp),
                                onClick = { openBluetoothBottomSheet = false }) {
                                Text(text = "Voltar")
                            }
                        }

                    }


            }
        }
    }

}