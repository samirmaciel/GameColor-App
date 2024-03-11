package com.sm.gamecolor.view.drawScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sm.gamecolor.domain.UserGame
import com.sm.gamecolor.ui.theme.green


@Composable
fun BluetoothUserGameList(
    userGameList: List<UserGame>,
    onClick: (UserGame) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.Top
    ) {
        items(userGameList) { userGame ->
            Row {
                Text(
                    text = userGame.name,
                    modifier = Modifier
                        .clickable { onClick(userGame) }
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = if(userGame.isParied) "Pariado" else "",
                    color = green,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }

        }
    }
}
