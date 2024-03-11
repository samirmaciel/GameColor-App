package com.sm.gamecolor.view.drawScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sm.gamecolor.domain.ConnectionState
import com.sm.gamecolor.view.drawScreen.viewModel.DrawScreenViewModel


@Composable
fun WaitingScreen(viewModel: DrawScreenViewModel){

    val deviceConnectionState by viewModel.deviceConnectionState.collectAsState()

    when(deviceConnectionState.connectionState){

        ConnectionState.CONNECTING -> {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Text(text = "Aguardando conexão!")
                Button(onClick = { viewModel.goToInitialScreen() }) {
                    Text(text = "Cancelar")
                }
            }
        }

        ConnectionState.CONNECTED -> {
            DrawingScreen(viewModel = viewModel)
        }

        else -> {
            viewModel.goToInitialScreen()
        }
    }



}