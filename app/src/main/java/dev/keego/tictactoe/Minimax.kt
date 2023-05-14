package dev.keego.tictactoe

import android.util.Log

object Minimax {
    fun minMaxDecisionModeEasy(boardState: BoardState): Int {
        val possibleMoves: ArrayList<BoardState> = successorsOf(boardState)
        val movesList = ArrayList<Int>()
        for (states in possibleMoves) {
            movesList.add(minValue(states))
        }
        var max = movesList[0]
        var bestIndex = 0
        for (i in 1 until movesList.size) {
            if (movesList[i] > max) {
                max = movesList[i]
                bestIndex = i
            }
        }
        println(possibleMoves)
        println(movesList)
        val action = possibleMoves[bestIndex].position

        Log.i("Minimax", possibleMoves.toString())
        Log.i("Minimax", movesList.toString())
//        val action = possibleMoves[bestIndex].position
        Log.i("Minimax", "Action: $action")
        return action
    }

    fun minMaxDecisionModeHard(boardState: BoardState): Int {
        val possibleMoves: ArrayList<BoardState> = successorsOf(boardState)
        val movesList = ArrayList<Int>()
        for (states in possibleMoves) {
            movesList.add(minimax(states, 0, false))
        }
        var max = movesList[0]
        var bestIndex = 0
        for (i in 1 until movesList.size) {
            if (movesList[i] > max) {
                max = movesList[i]
                bestIndex = i
            }
        }
        println(possibleMoves)
        println(movesList)
        val action = possibleMoves[bestIndex].position

        Log.i("Minimax", possibleMoves.toString())
        Log.i("Minimax", movesList.toString())
//        val action = possibleMoves[bestIndex].position
        Log.i("Minimax", "Action: $action")
        return action
//        return 0
    }

    fun minimax(boardState: BoardState, depth: Int, isMaximizingPlayer: Boolean): Int {
        if (isTerminal(boardState)) {
            val score = utilityOf(boardState)
            if (score == 10) {
                Log.i("X winn", "minimax: $score - $depth $isMaximizingPlayer")
                return score - depth
            } else if (score == -10) {
                Log.i("O win", "minimax: $score + $depth $isMaximizingPlayer")
                return score + depth
            }
            return score
        }
        if (isMaximizingPlayer) {
            var bestValue = -1000
            for (possibleMove in successorsOf(boardState)) {
                val value = minimax(possibleMove, depth + 1, false)
                bestValue = Math.max(bestValue, value)
                Log.i("Max value", "minimax: $value $bestValue + $depth $isMaximizingPlayer")
            }
            return bestValue
        } else {
            var bestValue = 1000
            for (possibleMove in successorsOf(boardState)) {
                val value = minimax(possibleMove, depth + 1, true)
                bestValue = Math.min(bestValue, value)
                Log.i("Min value", "minimax: $value $bestValue + $depth $isMaximizingPlayer")

            }
            return bestValue
        }
    }

    //Picks best option for the X-player
    private fun maxValue(boardState: BoardState): Int {
        if (isTerminal(boardState)) {
            return utilityOf(boardState)
        }
        var v = (-Double.POSITIVE_INFINITY).toInt()
        for (possibleMove in successorsOf(boardState)) {
            v = Math.max(v, minValue(possibleMove))
        }
        Log.i("Minimax Max", v.toString())
        return v
    }

    //Picks best option for the O-player
    private fun minValue(boardState: BoardState): Int {
        if (isTerminal(boardState)) {
            return utilityOf(boardState)
        }
        var v = Double.POSITIVE_INFINITY.toInt()
        for (possibleMove in successorsOf(boardState)) {
            v = Math.min(v, maxValue(possibleMove))
        }
        Log.i("Minimax Min", v.toString())
        return v
    }

    //Returns true if the game is over
    fun isTerminal(boardState: BoardState): Boolean {
        var takenSpots = 0
        for (a in 0..8) {
            if (boardState.getStateIndex(a) == "X" || boardState.getStateIndex(a) == "O") {
                takenSpots++
            }
            val line = checkState(boardState, a)

            //Check for Winners
            if (line == "XXX") {
                return true
            } else if (line == "OOO") {
                return true
            }
            if (takenSpots == 9) {
                return true
            }
        }
        return false
    }

    //Returns +1 if X is winner
//Return -1 if O is winner
//Returns 0 if no one won
    fun utilityOf(boardState: BoardState): Int {
        for (a in 0..7) {
            val line = checkState(boardState, a)
            //Check for Winners
            if (line == "XXX") {
                return 10
            } else if (line == "OOO") {
                return -10
            }
        }
        return 0
    }

    //Find any win state if it exists
    private fun checkState(boardState: BoardState, a: Int): String {
        return when (a) {
            0 -> boardState.getStateIndex(0) + boardState.getStateIndex(1) + boardState.getStateIndex(
                2
            )

            1 -> boardState.getStateIndex(3) + boardState.getStateIndex(4) + boardState.getStateIndex(
                5
            )

            2 -> boardState.getStateIndex(6) + boardState.getStateIndex(7) + boardState.getStateIndex(
                8
            )

            3 -> boardState.getStateIndex(0) + boardState.getStateIndex(3) + boardState.getStateIndex(
                6
            )

            4 -> boardState.getStateIndex(1) + boardState.getStateIndex(4) + boardState.getStateIndex(
                7
            )

            5 -> boardState.getStateIndex(2) + boardState.getStateIndex(5) + boardState.getStateIndex(
                8
            )

            6 -> boardState.getStateIndex(0) + boardState.getStateIndex(4) + boardState.getStateIndex(
                8
            )

            7 -> boardState.getStateIndex(2) + boardState.getStateIndex(4) + boardState.getStateIndex(
                6
            )

            else -> ""
        }
    }

    //Returns all possible states form a given state
    private fun successorsOf(boardState: BoardState): java.util.ArrayList<BoardState> {
        val possibleMoves = java.util.ArrayList<BoardState>()
        var xMoves = 0
        var yMoves = 0

        //Calculate player turn
        for (s in boardState.board) {
            if (s == "X") {
                xMoves++
            } else if (s == "O") {
                yMoves++
            }
        }
        val player: String = if (xMoves < yMoves) {
            "X"
        } else {
            "O"
        }

        //Create all possible states
        for (i in 0..8) {
            val newState = boardState.board.toMutableList()
            if (newState[i] !== "X" && newState[i] !== "O") {
                newState[i] = player
                possibleMoves.add(BoardState(i, newState))
            }
        }
        Log.i("Minimax", "successorsOf: $possibleMoves")
        return possibleMoves
    }
}