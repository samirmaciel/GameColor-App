package com.sm.gamecolor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sm.gamecolor.ui.theme.GameColorTheme
import com.sm.gamecolor.view.drawScreen.DrawingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameColorTheme {
                DrawingScreen()
            }
        }
    }
}




