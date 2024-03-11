package com.sm.gamecolor.bluetooth

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sm.gamecolor.domain.Line

fun String.toLine(): Line {

    val result = this
    Log.d("TESTERESULT", "toLine: ${result}")

    val offSets = this.split("#")
    val startX = offSets[0].split(":")[0]
    val startY = offSets[0].split(":")[1]
    val endX = offSets[1].split(":")[0]
    val endY = offSets[1].split(":")[1]

    val newLine = try{
        Line(
            Offset(startX.toFloat(), startY.toFloat()) ,
            Offset(endX.toFloat(), endY.toFloat()),
            Color.Red,
            5.dp
        )
    }catch (e: Exception){
        Log.e("TESTERESULT", "toLine: ${e.message}", )
        null
    }

    if(newLine == null){
        return Line(Offset(123f, 123f), Offset(123f,123f), Color.Red, 1.dp)
    }


    return newLine
}

fun Line.toByteArray(): ByteArray {
    val startX = start.x
    val startY = start.y

    val endX = end.x
    val endY = end.y
    return "#$startX:$startY#$endX:$endY#".encodeToByteArray()
}