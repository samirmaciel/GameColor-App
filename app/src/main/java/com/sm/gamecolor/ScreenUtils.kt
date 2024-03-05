package com.sm.gamecolor

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ScreenUtils {


    @Composable
    fun getHightDp(): Dp {
        val configuration = LocalConfiguration.current
        return configuration.screenHeightDp.dp
    }
}