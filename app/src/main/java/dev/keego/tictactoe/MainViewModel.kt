package dev.keego.tictactoe

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.keego.tictactoe.state.State
import dev.keego.tictactoe.state.VimelStateHolder

data class Cell(val i: Int, val j: Int)

class MainViewModel : VimelStateHolder<MainViewModel.MainViewModelState>(
    MainViewModelState()
) {
    companion object {
        const val PLAYER = "O"
        const val COMPUTER = "X"
    }

    fun currentBoard(): List<ArrayList<String>> = currentState.board
    var singlePlayer by mutableStateOf(true)
        private set

    data class MainViewModelState(
        val board: List<ArrayList<String>> = List(3) { arrayListOf("", "", "") }
    ) : State

    var winner by mutableStateOf("")
        private set


    //this property will tell
    //if the game is over or not
    val isGameOver: Boolean
        get() = hasComputerWon() || hasPlayerWon() || availableCells.isEmpty()


    //These functions are checking
    //Weather the computer or player has won or not
    private fun hasComputerWon(): Boolean {
        if (currentBoard()[0][0] == currentBoard()[1][1] &&
            currentBoard()[0][0] == currentBoard()[2][2] &&
            currentBoard()[0][0] == COMPUTER ||
            currentBoard()[0][2] == currentBoard()[1][1] &&
            currentBoard()[0][2] == currentBoard()[2][0] &&
            currentBoard()[0][2] == COMPUTER
        ) {
            return true
        }

        for (i in currentBoard().indices) {
            if (
                currentBoard()[i][0] == currentBoard()[i][1] &&
                currentBoard()[i][0] == currentBoard()[i][2] &&
                currentBoard()[i][0] == COMPUTER ||
                currentBoard()[0][i] == currentBoard()[1][i] &&
                currentBoard()[0][i] == currentBoard()[2][i] &&
                currentBoard()[0][i] == COMPUTER
            ) {
                return true
            }
        }

        return false
    }

    private fun hasPlayerWon(): Boolean {
        if (currentBoard()[0][0] == currentBoard()[1][1] &&
            currentBoard()[0][0] == currentBoard()[2][2] &&
            currentBoard()[0][0] == PLAYER ||
            currentBoard()[0][2] == currentBoard()[1][1] &&
            currentBoard()[0][2] == currentBoard()[2][0] &&
            currentBoard()[0][2] == PLAYER
        ) {
            return true
        }

        for (i in currentBoard().indices) {
            if (
                currentBoard()[i][0] == currentBoard()[i][1] &&
                currentBoard()[i][0] == currentBoard()[i][2] &&
                currentBoard()[i][0] == PLAYER ||
                currentBoard()[0][i] == currentBoard()[1][i] &&
                currentBoard()[0][i] == currentBoard()[2][i] &&
                currentBoard()[0][i] == PLAYER
            ) {
                return true
            }
        }
        return false
    }


    //in this var we will store the computersMove
    var computersMove: Cell? = null

    //This property is giving us
    //a list of all the empty cells
    private val availableCells: List<Cell>
        get() {
            val cells = mutableListOf<Cell>()
            for (i in currentBoard().indices) {
                for (j in currentBoard().indices) {
                    if (currentBoard()[i][j].isEmpty()) {
                        cells.add(Cell(i, j))
                    }
                }
            }
            return cells
        }

    //this is our minimax function to calculate
    //the best move for the computer
    fun minimax(depth: Int, player: String): Int {
        val board = currentBoard()
        if (hasComputerWon()) return +1
        if (hasPlayerWon()) return -1

        if (availableCells.isEmpty()) return 0

        var min = Integer.MAX_VALUE
        var max = Integer.MIN_VALUE

        for (i in availableCells.indices) {
            val cell = availableCells[i]
            if (player == COMPUTER) {
                placeMove(cell, COMPUTER)
                val currentScore = minimax(depth - 1, PLAYER)
                max = Math.max(currentScore, max)

                if (currentScore >= 0) {
                    if (depth == 0) computersMove = cell
                }

                if (currentScore == 1) {
                    removeCell(cell)
                    break
                }

                if (i == availableCells.size - 1 && max < 0) {
                    if (depth == 0) computersMove = cell
                }

            } else if (player == PLAYER) {
                placeMove(cell, PLAYER)
                val currentScore = minimax(depth - 1, COMPUTER)
                min = Math.min(currentScore, min)

                if (min == -1) {
                    removeCell(cell)
                    break
                }
            }
            removeCell(cell)
        }

        return if (player == COMPUTER) max else min
    }

    fun removeCell(cell: Cell) {
        update {
            val list = List(3) { arrayListOf("", "", "") }
            for (i in list.indices) {
                for (j in list.indices) {
                    list[i][j] = currentBoard()[i][j]
                }
            }
            it.copy(board = list.apply {
                this[cell.i][cell.j] = ""
            })
        }
    }
    val currentPlayer = mutableStateOf(PLAYER)

    //this function is placing a move in the given cell
    fun placeMove(cell: Cell, player: String) {
        if (currentPlayer.value == COMPUTER) {
            computersMove = null
            minimax(0, COMPUTER)
            computersMove?.let { placeMove(it, COMPUTER) }
            return
        }
        update {
            val list = List(3) { arrayListOf("", "", "") }
            for (i in list.indices) {
                for (j in list.indices) {
                    list[i][j] = currentBoard()[i][j]
                }
            }
            it.copy(board = list.apply {
                this[cell.i][cell.j] = player
            })
        }
        Log.i("MainViewModel", "placeMove: $cell")
        currentPlayer.value = if (player == PLAYER) COMPUTER else PLAYER
    }

    fun resetGame() {
        update {
            it.copy(board = List(3) { arrayListOf("", "", "") })
        }
        winner = ""
        currentPlayer.value = PLAYER
    }
}