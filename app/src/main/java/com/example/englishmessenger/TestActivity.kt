package com.example.englishmessenger

//import android.content.Intent
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
//import android.os.Handler
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

//import kotlinx.android.synthetic.main.activity_profile.*
//import kotlinx.android.synthetic.main.activity_test.*

data class TestQuestion(
//    val questionNumber: String = "",
    val question: String = "",
    val answer1: String = "",
    val answer2: String = "",
    val answer3: String = "",
    val answer4: String = "",
    val correctAnswer: String = ""
)

var quest_number = 1
var current_answer = ""
var correct_answer = ""
val language_level = mapOf<Int, String>(12 to "C1", 24 to "C2", 36 to "B1", 48 to "B2", 60 to "A1")

fun createQuestionFromMap(map: Map<String,Any>):TestQuestion{
    val question = map["question"] as? String?: throw IllegalArgumentException("Invalid value for question")
    val answer1 = map["answer1"] as? String?: throw IllegalArgumentException("Invalid value for answer1")
    val answer2 = map["answer2"] as? String?: throw IllegalArgumentException("Invalid value for answer2")
    val answer3 = map["answer3"] as? String?: throw IllegalArgumentException("Invalid value for answer3")
    val answer4 = map["answer4"] as? String?: throw IllegalArgumentException("Invalid value for answer4")
    val correctAnswer = map[map["rightAnswer"]] as? String?: throw IllegalArgumentException("Invalid value for correctAnswer")
    return  TestQuestion(question, answer1, answer2, answer3, answer4, correctAnswer)
}
//val questionsList = mutableListOf<TestQuestion>()

class TestActivity: AppCompatActivity(), CoroutineScope by MainScope() {
    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        FirebaseFirestore.setLoggingEnabled(true)
        setContentView(R.layout.activity_test)

//        val test: TextView = findViewById(R.id.TestName)
        val quest: TextView = findViewById(R.id.QuestionText)
        val ans1: Button = findViewById(R.id.Answer1btn)
        val ans2: Button = findViewById(R.id.Answer2btn)
        val ans3: Button = findViewById(R.id.Answer3btn)
        val ans4: Button = findViewById(R.id.Answer4btn)
        val conf: Button = findViewById(R.id.ConfirmBtn)
        ans1.isAllCaps = false
        ans2.isAllCaps = false
        ans3.isAllCaps = false
        ans4.isAllCaps = false
        conf.isAllCaps = false
        val progress: ProgressBar = findViewById(R.id.TestProgress)
        val warning: TextView = findViewById(R.id.warningTV)
        val res: TextView = findViewById(R.id.resTV)
        warning.text = "Please, choose an answer!"
        val s = 3
        var current = 0

//        fun commonColor() {
//            ans1.setBackgroundColor(Color.BLUE)
//            ans2.setBackgroundColor(Color.BLUE)
//            ans3.setBackgroundColor(Color.BLUE)
//            ans4.setBackgroundColor(Color.BLUE)
//            conf.setBackgroundColor(Color.BLUE)
//        }

        fun newQuestion(question_info: TestQuestion) {
            current_answer = ""
            warning.isVisible = false
            quest.text = question_info.question
            ans1.text = question_info.answer1
            ans2.text = question_info.answer2
            ans3.text = question_info.answer3
            ans4.text = question_info.answer4
            correct_answer = question_info.correctAnswer
            if (quest_number < s) conf.text = "Next question" else conf.text = "End test"
            progress.progress = current
            current += 1
//            commonColor()
        }


        fun result(){
            quest_number = 0
//            test.isVisible = false
            warning.isVisible = false
            progress.isVisible = false
            ans1.isVisible = false
            ans2.isVisible = false
            ans3.isVisible = false
            ans4.isVisible = false
            quest.isVisible = false
            conf.text = "Next"
            res.text = "Your language level is B2!"
            res.isVisible = true
        }

        val database = FirebaseFirestore.getInstance()
        val questionsCollectionRef = database.collection("englishTest").document("questions")

        suspend fun getQuestion(questNumb:Int): Map<String, Any>? {
            val mapName = "Question $questNumb"
            val documentSnapshot: DocumentSnapshot = try { questionsCollectionRef.get().await()
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

            if (documentSnapshot.exists()) {
                val data = documentSnapshot.data
                if (data != null && data.containsKey(mapName)) {
                    return data[mapName] as? Map<String, Any>
//                    val map = data[mapName] as? Map<String, Any>
//                    Log.d(TAG, "$map")
//                    return map
                }
            }

            return null
        }

        suspend fun startTest() {
            var correct_answers_number = 0
            getQuestion(quest_number)?.let { createQuestionFromMap(it) }?.let { newQuestion(it) }
            conf.setOnClickListener {
                if (conf.text == "Next") {
                    val intent1 = Intent(this, ProfileActivity::class.java)
                    startActivity(intent1)
                } else {
                    if (current_answer == "") warning.isVisible = true
                    else {
                        if (quest_number < s) {
                            if (current_answer == correct_answer) correct_answers_number += 1
                            quest_number += 1
                            launch { startTest() }
                        } else {
                            result()
                        }
                    }
                }
            }
        }

        launch { startTest() }

//        fun begin(){
//            quest_number = 0
//            test.isVisible = false
//            warning.isVisible = false
//            progress.isVisible = false
//            ans1.isVisible = false
//            ans1.isVisible = false
//            ans1.isVisible = false
//            ans1.isVisible = false
//            quest.isVisible = false
//            conf.text = "Start test"
//            res.text = "Now your current level is not defined. To determine it, you have to pass this test. " +
//                    " Please pass this test without any other help so that we can accurately beat your English level."
//            res.isVisible = true
//            conf.setOnClickListener {
//                test.isVisible = true
//                warning.isVisible = false
//                progress.isVisible = true
//                ans1.isVisible = true
//                ans1.isVisible = true
//                ans1.isVisible = true
//                ans1.isVisible = true
//                quest.isVisible = true
//                res.isVisible = false
//                launch { startTest() }
//            }
//        }

//        begin()

        ans1.setOnClickListener {
            current_answer = ans1.text.toString()
//            commonColor()
//            ans1.setBackgroundColor(Color.RED)
        }
        ans2.setOnClickListener {
            current_answer = ans2.text.toString()
//            commonColor()
//            ans2.setBackgroundColor(Color.RED)
        }
        ans3.setOnClickListener {
            current_answer = ans3.text.toString()
//            commonColor()
//            ans3.setBackgroundColor(Color.RED)
        }
        ans4.setOnClickListener {
            current_answer = ans4.text.toString()
//            commonColor()
//            ans4.setBackgroundColor(Color.RED)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        cancel() // Отмена сопрограммы при уничтожении активности
    }
}