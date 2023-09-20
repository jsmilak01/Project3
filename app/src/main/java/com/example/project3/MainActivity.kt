package com.example.project3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.ListPopupWindow.MATCH_PARENT
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var numQuestions = 10
    var difficulty = ""
    var operation = ""
    var numQuestionsCorrect = 0
    var startProblems = false
    var curAnswer = 0.0
    var numQuestionsAsked = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.decrease_num_questions).setOnClickListener{
            numQuestions--
            findViewById<TextView>(R.id.num_questions).text = "$numQuestions"
        }

        findViewById<Button>(R.id.increase_num_questions).setOnClickListener{
            numQuestions++
            findViewById<TextView>(R.id.num_questions).text = "$numQuestions"
        }

        findViewById<Button>(R.id.start_button).setOnClickListener {
            var difficultyId = findViewById<RadioGroup>(R.id.difficulty_group).checkedRadioButtonId
            if(difficultyId == R.id.difficulty_easy){
                difficulty="easy"
            }else if(difficultyId == R.id.difficulty_medium){
                difficulty="medium"
            }else if(difficultyId == R.id.difficulty_hard){
                difficulty="hard"
            }

            var operationId = findViewById<RadioGroup>(R.id.operation_group).checkedRadioButtonId
            if(operationId == R.id.operation_addition){
                operation="+"
            }else if(operationId == R.id.operation_subtraction){
                operation="-"
            }else if(operationId == R.id.operation_multiplication){
                operation="*"
            }else if(operationId == R.id.operation_division){
                operation="/"
            }

            numQuestions = findViewById<TextView>(R.id.num_questions).text.toString().toInt()

            if(difficulty!="" && operation!="") {
                setContentView(R.layout.problem_screen)
                findViewById<TextView>(R.id.num_questions_correct).text= "0/$numQuestions"
                findViewById<TextView>(R.id.problem_text_view).text = generateProblem()
                numQuestionsAsked++
            }
        }

        findViewById<Button>(R.id.done_button).setOnClickListener{
            var userAnswer = findViewById<EditText>(R.id.user_answer_text).text.toString().toDouble()
            findViewById<EditText>(R.id.user_answer_text).text.clear()
            if(userAnswer==curAnswer){
                numQuestionsCorrect++
                findViewById<TextView>(R.id.num_questions_correct).text = "$numQuestionsCorrect/$numQuestions"
            }
            if(numQuestionsAsked==numQuestions){
                setContentView(R.layout.finish_screen)
                findViewById<TextView>(R.id.final_score_text).text = "You got $numQuestionsCorrect out of $numQuestions."
            }else {
                var curText = findViewById<TextView>(R.id.problem_text_view).text.toString()
                findViewById<TextView>(R.id.problem_text_view).text =
                    curText + "\n" + generateProblem()
                numQuestionsAsked++
            }
        }

        findViewById<Button>(R.id.do_it_again_button).setOnClickListener {
            setContentView(R.layout.activity_main)
            numQuestions = 10
            difficulty = ""
            operation = ""
            numQuestionsCorrect = 0
            startProblems = false
            curAnswer = 0.0
            numQuestionsAsked = 0
        }

    }

    fun generateProblem(): String{
        var problem = ""
        var max = 0
        when(difficulty) {
            "easy" -> max = 10
            "medium" -> max = 25
            "hard" -> max = 50
            else -> {}
        }

        var num1 = Random.nextInt(0,max).toDouble()
        var num2 = Random.nextInt(0,max).toDouble()

        when(operation) {
            "+" -> curAnswer = num1+num2
            "-" -> curAnswer = num1-num2
            "*" -> curAnswer = num1*num2
            "/" -> curAnswer = num1/num2
            else -> {}
        }

        problem = "$num1   $operation   $num2"

        return problem
    }
}