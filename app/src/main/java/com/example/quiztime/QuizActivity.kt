package com.example.quiztime

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.quiztime.databinding.ActivityQuizBinding
import com.example.quiztime.databinding.ScoreDialogBinding

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        var questionModelList: List<QuestionModel> = listOf()
        var time: String = ""
    }

    lateinit var binding: ActivityQuizBinding
    var currentQuestionIndex = 0
    var selectedAnswer = ""
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            btn4.setOnClickListener(this@QuizActivity)
            nextBtn.setOnClickListener(this@QuizActivity)
            prevBtn.setOnClickListener(this@QuizActivity)  // Ensure prevBtn click listener is set
        }
        loadQuestion()
        startTimer()
    }

    private fun startTimer() {
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text =
                    String.format("%02d:%02d", minutes, remainingSeconds)

                // changes color of timer as per remaining time
                if (seconds < 60) {
                    binding.timerIndicatorTextview.setTextColor(Color.RED)
                } else {
                    binding.timerIndicatorTextview.setTextColor(Color.GREEN)
                }
            }

            override fun onFinish() {
                finishQuiz() // Call finishQuiz when the timer finishes
            }
        }.start()
    }

    private fun loadQuestion() {
        selectedAnswer = ""
        if (currentQuestionIndex == questionModelList.size) {
            finishQuiz()
            return
        }
        binding.apply {
            questionIndicatorTextview.text =
                "Question ${currentQuestionIndex + 1}/${questionModelList.size}"
            questionProgressIndicator.progress =
                (currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100).toInt()
            questionTextview.text = questionModelList[currentQuestionIndex].question
            btn1.text = questionModelList[currentQuestionIndex].options[0]
            btn2.text = questionModelList[currentQuestionIndex].options[1]
            btn3.text = questionModelList[currentQuestionIndex].options[2]
            btn4.text = questionModelList[currentQuestionIndex].options[3]
        }
    }

    override fun onClick(v: View?) {
        val clickedBtn = v as Button

        binding.apply {
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))
            btn4.setBackgroundColor(getColor(R.color.gray))
        }

        when (clickedBtn.id) {
            R.id.next_btn -> {
                // next button
                if (selectedAnswer.isEmpty()) {
                    Toast.makeText(
                        applicationContext,
                        "Please select an answer to continue",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (selectedAnswer == questionModelList[currentQuestionIndex].correct) {
                    score++
                    Log.i("Score of quiz", score.toString())
                }
                currentQuestionIndex++
                loadQuestion()
            }
            R.id.prev_btn -> {
                // prev button
                Log.d("QuizActivity", "Previous button clicked")
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--
                    score--
                    loadQuestion()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "You're already at the first question",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else -> {
                // other button clicked
                selectedAnswer = clickedBtn.text.toString()
                clickedBtn.setBackgroundColor(getColor(R.color.grey))
            }
        }
    }

    private fun finishQuiz() {
        val totalQuestion = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestion.toFloat()) * 100).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressTextview.text = "$percentage %"
            if (percentage >= 60) {
                scoreTitle.text = "Congrats! You have passed"
                scoreTitle.setTextColor(Color.BLUE)
            } else {
                scoreTitle.text = "Oops! You have failed"
                scoreTitle.setTextColor(Color.RED)
            }
            scoresubtitle.text = "$score out of $totalQuestion are correct"
            finishBtn.setOnClickListener() {
                finish()
            }
        }

        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
    }
}
