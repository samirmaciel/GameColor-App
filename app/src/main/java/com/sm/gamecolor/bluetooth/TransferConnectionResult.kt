package com.sm.gamecolor.bluetooth

import com.sm.gamecolor.domain.Line

sealed interface TransferConnectionResult {
    object ConnectionEstablished: TransferConnectionResult
    data class TransferSucceeded(val line: Line): TransferConnectionResult
    data class Error(val message: String): TransferConnectionResult
}
