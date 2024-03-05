package com.sm.gamecolor.view.drawScreen

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sm.gamecolor.ScreenUtils
import com.sm.gamecolor.domain.Line

@Composable
fun DrawingScreen() {
    val lines = remember {
        mutableStateListOf<Line>()
    }

    var colorSelectionCardExpanded by remember { mutableStateOf(false) }
    var lineColor by remember {
        mutableStateOf(Color.Black)
    }
    var lineStroke by remember {
        mutableStateOf(10.dp)
    }



    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    colorSelectionCardExpanded = false

                    val line = Line(
                        start = change.position - dragAmount,
                        end = change.position,
                        color = lineColor,
                        strokeWidth = lineStroke
                    )

                    lines.add(line)
                }
            }
    ) {
        lines.forEach { line ->
            drawLine(
                color = line.color,
                start = line.start,
                end = line.end,
                strokeWidth = line.strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        }
    }

    ColorSelectionCard(isExpanded = colorSelectionCardExpanded){ colorSelected ->
        lineColor = colorSelected
    }
    PincelSelectionCard(isExpanded = colorSelectionCardExpanded){ strokeSelected ->
        Log.d("DrawScreen", "DrawingScreen: ${strokeSelected}")
        lineStroke = strokeSelected
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {

        FloatingActionButton(modifier = Modifier.padding(16.dp), shape = CircleShape, onClick = {
            colorSelectionCardExpanded = !colorSelectionCardExpanded
        }) {
            Icon(Icons.Filled.Edit, "Color selection action floating button")
        }
    }
}


@Composable
fun ColorSelectionCard(isExpanded: Boolean = false, onColorSelected: (Color) -> Unit){

    val colorSelectionCardOffSetX: Dp by animateDpAsState(if (isExpanded) 0.dp else -50.dp)
    var colorSelected by remember {
        mutableStateOf(Color.Black)
    }


    Card(
        modifier = Modifier
            .size(width = 50.dp, height = 700.dp)
            .offset(x = colorSelectionCardOffSetX),
        colors = CardDefaults.cardColors( containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SmallFloatingActionButton(modifier = Modifier
                .size(40.dp)
                .border(
                    4.dp,
                    if (colorSelected == Color.Green) Color.Black else Color.Green,
                    CircleShape
                ),
                shape = CircleShape,
                containerColor = Color.Green,
                onClick = {
                    colorSelected = Color.Green
                    onColorSelected(Color.Green)
                }) {}

            SmallFloatingActionButton(modifier = Modifier
                .size(40.dp)
                .border(
                    4.dp,
                    if (colorSelected == Color.Black) Color.Gray else Color.Black,
                    CircleShape
                ),
                shape = CircleShape,
                containerColor = Color.Black,
                onClick = {
                    colorSelected = Color.Black
                    onColorSelected(Color.Black)
                }) {}
            SmallFloatingActionButton(modifier = Modifier
                .size(40.dp)
                .border(
                    4.dp,
                    if (colorSelected == Color.Blue) Color.Black else Color.Blue,
                    CircleShape
                ), shape = CircleShape, containerColor = Color.Blue, onClick = {
                colorSelected = Color.Blue
                onColorSelected(Color.Blue)
            }) {}
            SmallFloatingActionButton(modifier = Modifier
                .size(40.dp)
                .border(
                    4.dp,
                    if (colorSelected == Color.Red) Color.Black else Color.Red,
                    CircleShape
                ),
                shape = CircleShape,
                containerColor = Color.Red,
                onClick = {
                    colorSelected = Color.Red
                    onColorSelected(Color.Red)
                }) {}
        }

    }
}

@Composable
fun PincelSelectionCard(isExpanded: Boolean, onPincelStrokeSelected: (Dp) -> Unit){
    val yScreenDp = ScreenUtils.getHightDp()

    val offSetAnimation: Dp by animateDpAsState(if(isExpanded) yScreenDp - 100.dp else yScreenDp)
    var sliderPosition by remember { mutableFloatStateOf(0.2f) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(100f)
            .offset(y = offSetAnimation),
        colors = CardDefaults.cardColors( containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Box(modifier = Modifier, contentAlignment = Alignment.BottomCenter){
            Slider(modifier = Modifier.padding(20.dp),  value = sliderPosition, onValueChange = {
                sliderPosition = it
                onPincelStrokeSelected(Dp(it * 50))
            })
        }


    }


}
