package com.sm.gamecolor.bluetooth

import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sm.gamecolor.domain.Line
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.ClassCastException

class BluetoothDataTransferService(
    private val socket: BluetoothSocket
) {
    fun listenForIncomingMessages(): Flow<Line> {
        return flow {
            if(!socket.isConnected) {
                return@flow
            }

            while(true) {
                try {
                    val objectInputStream = ObjectInputStream(socket.inputStream)
                    val result = objectInputStream.readObject()
                    if(result is Line){

                        Log.d("LINERECEIVED", "Line received = StartX = ${result.start.x} StartY = ${result.start.y} EndX = ${result.end.x} EndY = ${result.end.y}  ")

                        emit(
                            result
                        )
                    }
                } catch(e: IOException) {
                    Log.e(TAG, "listenForIncomingMessages: ${e.message} ")
                    throw TransferFailedException()
                } catch (e: ClassNotFoundException){
                    Log.e(TAG, "listenForIncomingMessages: ${e.message} ")
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun sendLine(line: Line): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val outputStream = socket.outputStream
                val objectOutputStream = ObjectOutputStream(outputStream)
                objectOutputStream.writeObject(line)
                objectOutputStream.flush()

                Log.d("LINERECEIVED", "Line Sended = StartX = ${line.start.x} StartY = ${line.start.y} EndX = ${line.end.x} EndY = ${line.end.y}  ")
            } catch(e: IOException) {
                e.printStackTrace()
                return@withContext false
            }

            true
        }
    }

    companion object {
        const val TAG = "BluetoothDataTransferService"
    }
}