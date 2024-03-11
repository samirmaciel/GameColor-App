package com.sm.gamecolor.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.sm.gamecolor.domain.UserGame

@SuppressLint("MissingPermission")
fun BluetoothDevice.toUserGame(isParied: Boolean): UserGame {
    return UserGame(
        name = name ?: "Sem nome",
        isParied = isParied,
        bluetoothAddress = address
    )
}