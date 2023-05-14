package dev.keego.tictactoe

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.keego.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by mainViewModel.state.collectAsState()
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        ButtonGrid(
                            board = state.board
                        ) { cell ->
                            mainViewModel.placeMove(cell, mainViewModel.currentPlayer.value)
                        }

                        if (mainViewModel.isGameOver) {
                            Box {
                                Text(
                                    text = "Game is Over: ${mainViewModel.winner}",
                                    fontSize = 20.sp
                                )
                            }
                        }

                        ResetButton(onClick = mainViewModel::resetGame)

                        //Play with a friend
                        TextButton(
                            onClick = {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Coming soon",
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .size(50.dp),
                        ) {
                            Text(
                                text = "Play with a friend",
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}