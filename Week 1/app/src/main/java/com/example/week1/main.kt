package com.example.week1
import kotlin.system.exitProcess
import kotlin.collections.mutableMapOf

fun main() {
    var playerName1: String
    var playerName2: String
    var board = Array(6) { Array(6) { "[ ]" } }
    var highScores = mutableMapOf<String, Int>()

    while (true) {
        println("Main Menu:")
        println("1. Game")
        println("2. High Score")
        println("3. Exit")
        print("Pilih menu: ")
        val menuChoice = readLine()?.toIntOrNull()

        when (menuChoice) {
            1 -> {
                playerName1 = enterPlayerName("Player 1")
                playerName2 = enterPlayerName("Player 2")

                initializeBoard(board)
                playOthello(playerName1, playerName2, board, highScores)
            }
            2 -> {
                println("High Score Menu:")
                if (highScores.isEmpty()) {
                    println("High score masih belum ada.")
                } else {
                    println("Top 3 High Scores:")
                    highScores.toList()
                        .sortedByDescending { (_, score) -> score }
                        .take(3)
                        .forEachIndexed { index, (player, score) ->
                            println("${index + 1}. $player - Score: $score")
                        }
                }
            }
            3 -> {
                println("Exiting...")
                exitProcess(0)
            }
            else -> println("Pilihan tidak valid, silakan pilih 1, 2, atau 3.")
        }
    }
}

fun enterPlayerName(playerNumber: String): String {
    var playerName: String
    do {
        print("Masukkan nama $playerNumber: ")
        playerName = readLine()?.trim() ?: ""
    } while (playerName.isEmpty())
    return playerName
}

fun initializeBoard(board: Array<Array<String>>) {
    for (i in 0 until board.size) {
        for (j in 0 until board[i].size) {
            board[i][j] = "[ ]"
        }
    }
    board[2][2] = "[o]"
    board[2][3] = "[x]"
    board[3][2] = "[x]"
    board[3][3] = "[o]"
}

fun playOthello(playerName1: String, playerName2: String, board: Array<Array<String>>, highScores: MutableMap<String, Int>) {
    var currentPlayer = 1
    var gameOver = false

    while (!gameOver) {
        printBoard(board)

        val currentPlayerName = if (currentPlayer == 1) playerName1 else playerName2
        println("$currentPlayerName's Turn")

        print("Enter row (1-6): ")
        val row = readLine()?.toIntOrNull()?.let { it - 1 }

        print("Enter column (1-6): ")
        val column = readLine()?.toIntOrNull()?.let { it - 1 }

        if (row != null && column != null && row in 0..5 && column in 0..5 && board[row][column] == "[ ]") {
            val symbol = if (currentPlayer == 1) "[o]" else "[x]"
            board[row][column] = symbol

            flipPieces(board, row, column, symbol)

            var countO = 0
            var countX = 0
            for (i in board.indices) {
                for (j in board[i].indices) {
                    if (board[i][j] == "[o]") countO++
                    if (board[i][j] == "[x]") countX++
                }
            }
            if (countO + countX == 36 || countO == 0 || countX == 0) {
                gameOver = true
                printBoard(board)
                if (countO > countX) {
                    println("$playerName1 Wins! Score: $countO")
                    highScores[playerName1] = countO
                } else if (countX > countO) {
                    println("$playerName2 Wins! Score: $countX")
                    highScores[playerName2] = countX
                } else {
                    println("It's a tie!")
                }
            } else {
                currentPlayer = 3 - currentPlayer
            }
        } else {
            println("Invalid input. Please enter valid coordinates.")
        }
    }
}

fun flipPieces(board: Array<Array<String>>, row: Int, column: Int, symbol: String) {
    val opponentSymbol = if (symbol == "[o]") "[x]" else "[o]"

    val directions = arrayOf(
        Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1),
        Pair(-1, -1), Pair(-1, 1), Pair(1, -1), Pair(1, 1)
    )

    for ((dx, dy) in directions) {
        var x = row + dx
        var y = column + dy
        var piecesToFlip = mutableListOf<Pair<Int, Int>>()

        while (x in 0..5 && y in 0..5 && board[x][y] == opponentSymbol) {
            piecesToFlip.add(Pair(x, y))
            x += dx
            y += dy
        }

        if (x in 0..5 && y in 0..5 && board[x][y] == symbol) {
            for ((flipX, flipY) in piecesToFlip) {
                board[flipX][flipY] = symbol
            }
        }
    }
}

fun printBoard(board: Array<Array<String>>) {
    println("   1  2  3  4  5  6")
    for (i in 0 until board.size) {
        print("${i + 1} ")
        for (j in 0 until board[i].size) {
            print(board[i][j])
        }
        println()
    }
}
