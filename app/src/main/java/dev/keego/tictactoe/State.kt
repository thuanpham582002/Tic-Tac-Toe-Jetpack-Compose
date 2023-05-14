package dev.keego.tictactoe

import java.util.Arrays

class State(var position: Int, var state: Array<String>) {

    fun getStateIndex(i: Int): String {
        return state[i]
    }

    fun changeState(i: Int, player: String) {
        state[i] = player
    }

    override fun toString(): String {
        return "dev.keego.tictactoe.State{" +
                "position=" + position +
                ", state=" + Arrays.toString(state) +
                '}'
    }
}