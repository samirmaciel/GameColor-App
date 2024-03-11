package com.sm.gamecolor.koin

import com.sm.gamecolor.bluetooth.AndroidBluetoothController
import com.sm.gamecolor.bluetooth.BluetoothController
import com.sm.gamecolor.view.drawScreen.viewModel.DrawScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<BluetoothController> { AndroidBluetoothController(get()) }
    viewModel { DrawScreenViewModel(get()) }
}