package com.example.pregexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import java.util.*
import kotlin.collections.ArrayList


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer: CountDownTimer ?= null
    private var restProcess = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseRestProcess = 0
    private var exericseTimerDuration: Long = 20

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePostion = -1

    private var tts: TextToSpeech? = null

    private var exerciseAdapter: ExerciseStatusAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        val toolbar_exercise_activity = findViewById<Toolbar>(R.id.toolbar_exercise_activity)
        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        tts = TextToSpeech(this, this)

        exerciseList = Constants.defaultExerciseList()
        setupRestView()

        setupExerciseStatusRecyclerView()

    }

    override fun onDestroy() {

        if (restTimer != null) {
            restTimer!!.cancel()
            restProcess = 0
        }

        if(exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseRestProcess = 0
        }

        if(tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        super.onDestroy()
    }

    private fun setupRestView(){

        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE

        if(restTimer != null) {
            restTimer!!.cancel()
            restProcess = 0
        }

        tvUpcomingExerciseName.text = exerciseList!![currentExercisePostion +1].getName()
        setRestProgressBar()
    }

    private fun setRestProgressBar (){
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = restProcess
        restTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                restProcess ++
                progressBar.progress = 10 - restProcess

                val tvTimer = findViewById<TextView>(R.id.tvTimer)
                tvTimer.text =(10 - restProcess).toString()
            }

            override fun onFinish() {

                currentExercisePostion ++

                exerciseList!![currentExercisePostion].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()
    }



    private fun setupExerciseView() {

        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer !!.cancel()
            exerciseRestProcess = 0
        }

        ivImage.setImageResource(exerciseList!![currentExercisePostion].getImage())
        exerciseTvName.text = exerciseList!![currentExercisePostion].getName()

        speakOut(exerciseList!![currentExercisePostion].getName())

        setExericiseProgressBar()
    }

    private fun setExericiseProgressBar() {
        var exerciseProgressBar = findViewById<ProgressBar>(R.id.exerciseProgressBar)
        exerciseProgressBar.progress = exerciseRestProcess
        exerciseTimer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseRestProcess ++

                exerciseProgressBar.progress = 20 - exerciseRestProcess

                val exerciseTvTimer = findViewById<TextView>(R.id.exerciseTvTimer)
                exerciseTvTimer.text = (20 - exerciseRestProcess).toString()
            }

            override fun onFinish() {

                exerciseList!![currentExercisePostion].setIsSelected(false)
                exerciseList!![currentExercisePostion].setIsCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()

                if(currentExercisePostion < exerciseList?.size!! -1 ) {
                    setupRestView()
                }else {

                    Toast.makeText(
                        this@ExerciseActivity, "Congratulations! You have completed the workout!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }

    override fun onInit(status: Int) {

        if(status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The language is not supported")
            }
        }else{
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "" )
    }

    private fun setupExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter = exerciseAdapter
    }



}