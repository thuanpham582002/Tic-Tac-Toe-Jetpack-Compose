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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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

    @OptIn(ExperimentalMaterial3Api::class)
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
                    Scaffold(
                        topBar = {
                            TicTacAppBar(state.isSinglePlayer,
                                state.isHard,
                                onModeChange = {
                                    mainViewModel.updateState(
                                        state.copy(
                                            isSinglePlayer = it
                                        )
                                    )
                                },
                                onDifficultyChange = {
                                    mainViewModel.updateState(
                                        state.copy(
                                            isHard = it
                                        )
                                    )
                                })
                        }) { paddingValues ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround,
                        ) {
                            ButtonGrid(
                                board = state.boardState.board,
                                onclick = mainViewModel::play,
                            )

                            if (state.isGameOver) {
                                Text(
                                    text = "Game is Over: ${state.winner} wins",
                                    fontSize = 20.sp
                                )
                            }
                            ResetButton(onClick = mainViewModel::resetGame)
                        }
                    }
                }
            }
        }
    }
}