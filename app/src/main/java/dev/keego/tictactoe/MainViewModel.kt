package dev.keego.tictactoe

import androidx.lifecycle.viewModelScope
import dev.keego.tictactoe.state.State
import dev.keego.tictactoe.state.VimelStateHolder
import kotlinx.coroutines.launch

data class Cell(val i: Int, val j: Int)
enum class Difficulty {
    EASY, HARD
}

class MainViewModel : VimelStateHolder<MainViewModel.MainViewModelState>(MainViewModelState()) {
    companion object {
        const val PLAYER = "O"
        const val COMPUTER = "X"
    }

    data class MainViewModelState(
        val boardState: BoardState = BoardState(0, listOf("", "", "", "", "", "", "", "", "")),
        val isGameOver: Boolean = false,
        val winner: String = "",
        val isSinglePlayer: Boolean = true,
        val currentPlayer: String = PLAYER,
        val isHard: Boolean = true
    ) : State

    fun updateState(state: State) {
        update {
            state as MainViewModelState
        }
    }

    fun play(move: Int = -1) {
        if (Minimax.isTerminal(currentState.boardState)) {
            return
        }

        if (currentState.currentPlayer == PLAYER) {
            update {
                it.copy(
                    boardState = BoardState(move, it.boardState.board.toMutableList().apply {
                        set(move, PLAYER)
                    }),
                    currentPlayer = COMPUTER
                )
            }
            if (currentState.isSinglePlayer) {
                play()
            }

        } else {
            update {
                it.copy(
                    boardState = BoardState(
                        0,
                        it.boardState.board.toMutableList().apply {
                            if (move != -1) set(move, COMPUTER)
                            else {
                                if (it.isHard)
                                    set(Minimax.minMaxDecisionModeHard(it.boardState), COMPUTER)
                                else
                                    set(Minimax.minMaxDecisionModeEasy(it.boardState), COMPUTER)
                            }
                        }),
                    currentPlayer = PLAYER
                )
            }
        }
        if (Minimax.isTerminal(currentState.boardState)) {
            when (Minimax.utilityOf(currentState.boardState)) {
                1 -> update {
                    it.copy(
                        isGameOver = true,
                        winner = COMPUTER
                    )
                }

                -1 -> update {
                    it.copy(
                        isGameOver = true,
                        winner = PLAYER
                    )
                }

                else -> update {
                    it.copy(
                        isGameOver = true,
                        winner = "Draw"
                    )
                }
            }
        }
    }

    fun resetGame() {
        update {
            MainViewModelState()
        }
    }
}