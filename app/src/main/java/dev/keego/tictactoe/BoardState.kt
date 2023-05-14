package dev.keego.tictactoe

class BoardState(var position: Int, var board: List<String>) {

    fun getStateIndex(i: Int): String {
        return board[i]
    }

    fun changeState(i: Int, player: String) {
        board = board.toMutableList().apply {
            set(i, player)
        }
    }

    override fun toString(): String {
        return "dev.keego.tictactoe.State{" +
                "position=" + position +
                ", state=" + board.toTypedArray().contentToString() +
                '}'
    }
}