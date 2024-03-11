package com.sm.gamecolor.bluetooth

import com.sm.gamecolor.domain.ConnectionState
import com.sm.gamecolor.domain.Line

data class DeviceConnectionState(
    var connectionState: ConnectionState? = null,
    var receivedLine: Line? = null,
    var errorMessage: String? = null
)
