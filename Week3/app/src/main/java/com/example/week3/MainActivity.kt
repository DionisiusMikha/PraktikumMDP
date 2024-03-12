package com.example.week3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private lateinit var BtnStart: Button
    private lateinit var PlayerName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BtnStart = findViewById(R.id.BtnStart)
        PlayerName = findViewById(R.id.PlayerName)

        BtnStart.setOnClickListener {
            var playerName = PlayerName.text.toString()
            if (playerName.isEmpty()) {
                PlayerName.error = "Please enter your name"
                return@setOnClickListener
            } else {
                val intent = Intent(this, Game::class.java)
                intent.putExtra("playerName", playerName)
                startActivity(intent)
            }
        }


    }
}