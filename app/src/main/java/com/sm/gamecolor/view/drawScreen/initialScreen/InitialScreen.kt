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
import com.sm.gamecolor.view.drawScreen.components.BluetoothUserGameList
import com.sm.gamecolor.view.drawScreen.components.CustomBottomSheet
import com.sm.gamecolor.view.drawScreen.viewModel.DrawScreenViewModel


@Composable
fun InitialScreen(viewModel: DrawScreenViewModel) {

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
                .padding(start = 40.dp, end = 40.dp), onClick = {  viewModel.goToDrawScreen() }) {
                Text(text = "Pintar")
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp), onClick = { openBluetoothBottomSheet = !openBluetoothBottomSheet }) {
                Text(text = "Pintar com amigo(a)")
            }

        }

        val userGameListState by viewModel.userGameList.collectAsState()

            CustomBottomSheet(
                height = 300.dp,
                isExpanded = openBluetoothBottomSheet,
                onDismissed = { }) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BluetoothUserGameList(userGameList = userGameListState, onClick = {

                    })
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Button(
                            modifier = Modifier.padding(10.dp),
                            onClick = { openBluetoothBottomSheet = false }) {
                            Text(text = "Cancelar")
                        }
                    }
                }
            }
        }

}