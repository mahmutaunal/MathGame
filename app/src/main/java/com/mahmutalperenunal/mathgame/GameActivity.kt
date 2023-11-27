package com.mahmutalperenunal.mathgame

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.mahmutalperenunal.mathgame.databinding.ActivityGameBinding
import java.util.Locale
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private lateinit var timer: CountDownTimer
    private val startTimerInMillis: Long = 60000
    private var timeLeftInMillis: Long = startTimerInMillis

    private var correctAnswer = 0
    private var userScore = 0
    private var userLife = 3

    private var gameType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameType = intent.getStringExtra("Game Type").toString()

        createQuestion()

        binding.checkButton.setOnClickListener {
            checkAnswer()
        }

        binding.nextButton.setOnClickListener {
            nextButtonAction()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun createQuestion() {
        binding.checkButton.isClickable = true

        val number1 = Random.nextInt(0, 100)
        val number2 = Random.nextInt(0, 100)

        when (gameType) {
            "Addition" -> {
                binding.questionTextView.text = "$number1 + $number2"
                correctAnswer = number1 + number2
            }

            "Subtraction" -> {
                if (number1 > number2) {
                    binding.questionTextView.text = "$number1 - $number2"
                    correctAnswer = number1 - number2
                } else {
                    binding.questionTextView.text = "$number2 - $number1"
                    correctAnswer = number2 - number1
                }
            }

            else -> {
                binding.questionTextView.text = "$number1 * $number2"
                correctAnswer = number1 * number2
            }
        }

        startTimer()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateTimerText()

                userLife--

                if (userLife < 0) {
                    userLife = 0
                }

                binding.lifeTextView.text = "$userLife"
                binding.questionTextView.text = "Sorry, Time is up!"
            }

        }.start()
    }

    private fun updateTimerText() {
        val remainingTime: Int = (timeLeftInMillis / 1000).toInt()
        binding.timeTextView.text = String.format(Locale.getDefault(), "%02d", remainingTime)
    }

    private fun pauseTimer() {
        timer.cancel()
    }

    private fun resetTimer() {
        timeLeftInMillis = startTimerInMillis
        updateTimerText()
    }

    @SuppressLint("SetTextI18n")
    private fun checkAnswer() {
        val input = binding.answerTextInput.text.toString()

        if (input == "") {
            Toast.makeText(
                applicationContext,
                "Please write an answer or click the next button!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            pauseTimer()

            val userAnswer = input.toInt()

            if (userAnswer == correctAnswer) {
                userScore += 10
                binding.scoreTextView.text = "$userScore"
                binding.questionTextView.text = "Congratulations, your answer is correct :)"
            } else {
                userLife--

                if (userLife < 0) {
                    userLife = 0
                }

                binding.lifeTextView.text = "$userLife"
                binding.questionTextView.text = "Sorry, your answer is wrong :( \n" +
                        "The correct answer is: $correctAnswer"
            }

            binding.checkButton.isClickable = false
        }
    }

    private fun nextButtonAction() {
        pauseTimer()
        resetTimer()
        binding.answerTextInput.setText("")

        if (userLife == 0) {
            Toast.makeText(applicationContext, "Game Over!", Toast.LENGTH_SHORT).show()

            val intent = Intent(applicationContext, ResultActivity::class.java)
            intent.putExtra("Score", userScore)
            startActivity(intent)
            finish()
        } else {
            createQuestion()
        }
    }
}