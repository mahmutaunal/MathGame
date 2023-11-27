package com.mahmutalperenunal.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mahmutalperenunal.mathgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAdd.setOnClickListener {
            val intent = Intent(applicationContext, GameActivity::class.java)
            intent.putExtra("Game Type", "Addition")
            startActivity(intent)
        }

        binding.buttonSub.setOnClickListener {
            val intent = Intent(applicationContext, GameActivity::class.java)
            intent.putExtra("Game Type", "Subtraction")
            startActivity(intent)
        }

        binding.buttonMulti.setOnClickListener {
            val intent = Intent(applicationContext, GameActivity::class.java)
            intent.putExtra("Game Type", "Multiplication")
            startActivity(intent)
        }
    }
}