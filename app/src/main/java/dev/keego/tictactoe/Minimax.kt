package dev.keego.tictactoe

object Minimax {
    fun minMaxDecision(state: State): Int {
        val possibleMoves: ArrayList<State> = successorsOf(state)
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
        println("Action: $action")
        return action
    }

    //Picks best option for the X-player
    private fun maxValue(state: State): Int {
        if (isTerminal(state)) {
            return utilityOf(state)
        }
        var v = (-Double.POSITIVE_INFINITY).toInt()
        for (possibleMove in successorsOf(state)) {
            v = Math.max(v, minValue(possibleMove))
        }
        println(v)
        return v
    }

    //Picks best option for the O-player
    private fun minValue(state: State): Int {
        if (isTerminal(state)) {
            return utilityOf(state)
        }
        var v = Double.POSITIVE_INFINITY.toInt()
        for (possibleMove in successorsOf(state)) {
            v = Math.min(v, maxValue(possibleMove))
        }
        println(v)
        return v
    }

    //Returns true if the game is over
    fun isTerminal(state: State): Boolean {
        var takenSpots = 0
        for (a in 0..8) {
            if (state.getStateIndex(a) == "X" || state.getStateIndex(a) == "O") {
                takenSpots++
            }
            val line = checkState(state, a)

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
    private fun utilityOf(state: State): Int {
        for (a in 0..7) {
            val line = checkState(state, a)
            //Check for Winners
            if (line == "XXX") {
                return 1
            } else if (line == "OOO") {
                return -1
            }
        }
        return 0
    }

    //Find any win state if it exists
    private fun checkState(state: State, a: Int): String {
        return when (a) {
            0 -> state.getStateIndex(0) + state.getStateIndex(1) + state.getStateIndex(2)
            1 -> state.getStateIndex(3) + state.getStateIndex(4) + state.getStateIndex(5)
            2 -> state.getStateIndex(6) + state.getStateIndex(7) + state.getStateIndex(8)
            3 -> state.getStateIndex(0) + state.getStateIndex(3) + state.getStateIndex(6)
            4 -> state.getStateIndex(1) + state.getStateIndex(4) + state.getStateIndex(7)
            5 -> state.getStateIndex(2) + state.getStateIndex(5) + state.getStateIndex(8)
            6 -> state.getStateIndex(0) + state.getStateIndex(4) + state.getStateIndex(8)
            7 -> state.getStateIndex(2) + state.getStateIndex(4) + state.getStateIndex(6)
            else -> ""
        }
    }

    //Returns all possible states form a given state
    private fun successorsOf(state: State): java.util.ArrayList<State> {
        val possibleMoves = java.util.ArrayList<State>()
        var xMoves = 0
        var yMoves = 0
        val player: String

        //Calculate player turn
        for (s in state.state) {
            if (s == "X") {
                xMoves++
            } else if (s == "O") {
                yMoves++
            }
        }
        player = if (xMoves <= yMoves) {
            "X"
        } else {
            "O"
        }

        //Create all possible states
        for (i in 0..8) {
            val newState = state.state.clone()
            if (newState[i] !== "X" && newState[i] !== "O") {
                newState[i] = player
                possibleMoves.add(State(i, newState))
            }
        }
        return possibleMoves
    }
}