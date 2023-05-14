package dev.keego.tictactoe

import android.text.style.TtsSpan.TimeBuilder
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.keego.tictactoe.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacAppBar(singlePlayer: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val checkedState = remember { mutableStateOf(singlePlayer) }
    TopAppBar(
        title = { Text(text = "Tic Tac Toe", color = Color.White) },
        actions = {
            Row(modifier = Modifier.padding(end = 16.dp)) {
                Text(text = if (checkedState.value) "Single Player" else "Multi Player")
                Spacer(modifier = Modifier.size(16.dp))
                Switch(checked = checkedState.value, onCheckedChange = {
                    checkedState.value = it
                    onCheckedChange(it)
                })
            }
        }
    )
}

@Composable
fun ResetButton(onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier
        .padding(16.dp)
        .size(50.dp)) {
        Text(
            text = "Restart",
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ButtonGrid(board: List<List<String>>, onclick: (Cell) -> Unit) {
    Log.i("TicTacToeScreen", "ButtonGrid: $board")
    Column(verticalArrangement = Arrangement.SpaceEvenly) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            TicTacToeButton(text = board[0][0]) { onclick(Cell(0,0)) }
            TicTacToeButton(text = board[0][1]) { onclick(Cell(0,1)) }
            TicTacToeButton(text = board[0][2]) { onclick(Cell(0,2)) }
        }

        Row(horizontalArrangement = Arrangement.SpaceAround) {
            TicTacToeButton(text = board[1][0]) { onclick(Cell(1,0)) }
            TicTacToeButton(text = board[1][1]) { onclick(Cell(1,1)) }
            TicTacToeButton(text = board[1][2]) { onclick(Cell(1,2)) }
        }
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            TicTacToeButton(text = board[2][0]) { onclick(Cell(2,0)) }
            TicTacToeButton(text = board[2][1]) { onclick(Cell(2,1)) }
            TicTacToeButton(text = board[2][2]) { onclick(Cell(2,2)) }
        }
    }
}

@Composable
fun TicTacToeButton(text: String, onclick: () -> Unit) {
    Box(modifier = Modifier.padding(8.dp)) {
        TextButton(
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, Purple80),
            onClick = onclick,
            enabled = text.isBlank()
        ) {
            Text(
                text = text,
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .fillMaxHeight()
            )
        }
    }
}