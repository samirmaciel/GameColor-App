package com.sm.gamecolor.bluetooth

import com.sm.gamecolor.domain.Line
import com.sm.gamecolor.domain.UserGame

sealed class ScanDevicesState {

    data object Scanning: ScanDevicesState()
    data object ScanNotFoundDevices: ScanDevicesState()
    data object  FoundedDevices: ScanDevicesState()

}