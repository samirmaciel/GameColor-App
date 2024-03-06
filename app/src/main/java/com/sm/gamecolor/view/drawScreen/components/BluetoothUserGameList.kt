package com.sm.gamecolor.view.drawScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sm.gamecolor.domain.UserGame


@Composable
fun BluetoothUserGameList(
    userGameList: List<UserGame>,
    onClick: (UserGame) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(userGameList) { userGame ->
            Text(
                text = userGame.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(userGame) }
                    .padding(16.dp)
            )
        }
    }
}
