package com.sm.gamecolor.view.drawScreen.viewModel

import androidx.lifecycle.ViewModel
import com.sm.gamecolor.domain.ScreenSelection
import com.sm.gamecolor.domain.UserGame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DrawScreenViewModel : ViewModel() {

    private val _stateUI = MutableStateFlow(ScreenSelection.INITIAL)
    val stateUI = _stateUI.asStateFlow()

    private val _UserGameList = MutableStateFlow(listOf<UserGame>())
    val userGameList = _UserGameList.asStateFlow()


    fun goToDrawScreen(){
        _stateUI.value = ScreenSelection.DRAW
    }

    fun goToInitialScreen() {
        _stateUI.value = ScreenSelection.INITIAL
    }
}