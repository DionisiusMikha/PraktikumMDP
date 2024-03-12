package com.example.week3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat


class Game : AppCompatActivity() {

    private lateinit var playerName: TextView
    private lateinit var playerScore: TextView
    private lateinit var btnQuit: Button
    private lateinit var btnRestart: Button
    private var lastClickTime: Long = 0
    private val DOUBLE_CLICK_TIME_DELTA: Long = 300
    private val TOTAL_BUTTONS = 30
    var score = 0
    private val colors = arrayOf(
        R.color.pink,
        R.color.blue,
        R.color.green,
        R.color.orange
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        playerName = findViewById(R.id.InGameName)
        playerScore = findViewById(R.id.ScorePoint)
        btnQuit = findViewById(R.id.BtnQuit)
        btnRestart = findViewById(R.id.BtnRestart)

        btnQuit.setBackgroundColor(resources.getColor(R.color.red))
        btnQuit.setTextColor(resources.getColor(R.color.white))

        val name = intent.getStringExtra("playerName")
        playerName.text = name
        playerScore.text = score.toString()

        btnQuit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        btnRestart.setOnClickListener {
            score = 0
            playerScore.text = score.toString()
            initializeButtons()
        }

        playerName.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                generatePurpleButton()
                Toast.makeText(this, "CHEAT ACTIVATED", Toast.LENGTH_SHORT).show()
            }
            lastClickTime = currentTime
        }


        initializeButtons()

    }

    private fun initializeButtons() {
        val buttons = mutableListOf<Button>()

        val buttonsPerColor = TOTAL_BUTTONS / colors.size
        val remainingButtons = TOTAL_BUTTONS % colors.size
        val randomIndexes = (0 until colors.size).shuffled()

        val colorIndicesList = mutableListOf<Int>()
        for (i in 0 until colors.size) {
            colorIndicesList.addAll(List(buttonsPerColor + if (i < remainingButtons) 1 else 0) { i })
        }
        colorIndicesList.shuffle()

        for (i in 0 until TOTAL_BUTTONS) {
            val buttonId = resources.getIdentifier("dot${i + 1}", "id", packageName)
            val button = findViewById<Button>(buttonId)
            val colorIndex = colorIndicesList[i]
            button.setBackgroundColor(ContextCompat.getColor(this, colors[randomIndexes[colorIndex]]))
            buttons.add(button)
        }
    }

    private fun generatePurpleButton() {
        val randomIndex = (0 until TOTAL_BUTTONS).random()
        val buttonId = resources.getIdentifier("dot${randomIndex + 1}", "id", packageName)
        val button = findViewById<Button>(buttonId)
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.purple))
    }
}