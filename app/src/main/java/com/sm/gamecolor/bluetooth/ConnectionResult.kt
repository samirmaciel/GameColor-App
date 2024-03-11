package com.sm.gamecolor.bluetooth

import com.sm.gamecolor.domain.Line

sealed interface ConnectionResult {
    object ConnectionEstablished: ConnectionResult
    data class TransferSucceeded(val line: Line): ConnectionResult
    data class Error(val message: String): ConnectionResult
}