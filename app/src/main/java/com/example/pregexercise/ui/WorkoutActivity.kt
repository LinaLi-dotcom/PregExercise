package com.example.pregexercise.ui 

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pregexercise.BaseActivity
import com.example.pregexercise.R
import com.example.pregexercise.databinding.ActivityWorkoutBinding
import com.example.pregexercise.models.WorkoutModel
import com.example.pregexercise.ui.adepters.WorkoutStatusAdapter
import com.example.pregexercise.utils.Constants
import java.util.*
import kotlin.collections.ArrayList

class WorkoutActivity : BaseActivity(){

    private lateinit var binding: ActivityWorkoutBinding

    private var mSetId: String = ""
    private var mSetName: String = ""
    private var mExerciseId: String = ""

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    // pause setting
    private var pauseOffset:Long = 0
    private var isTimerActive: Boolean = true

    var restTimerDuration: Int = 2
    var exerciseTimerDuration: Int = 3
    private var mWorkoutListItems: ArrayList<WorkoutModel>? = null
    private var currentExercisePosition = -1

    // Sound and Speech
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    //RecyclerView
    private var exerciseAdapter: WorkoutStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Constants.INTENT_WORKOUT_SET_ID)) {
            mSetId = intent.getStringExtra(Constants.INTENT_WORKOUT_SET_ID)!!
        }

        if (intent.hasExtra(Constants.INTENT_WORKOUT_EXERCISE_ID)) {
            mExerciseId = intent.getStringExtra(Constants.INTENT_WORKOUT_EXERCISE_ID)!!
        }

        // Start and pause timer : optional
        binding.exerciseProgressBar.setOnClickListener {  checkPauseTimer()  }
        binding.ivImage.setOnClickListener {  checkPauseTimer()  }

        // Get exercise List
        mWorkoutListItems = getRowsData()
        // get Current Exercise
        currentExercisePosition = getCurrentExercisePosition()
        // Start with rest timer
        setupRestView()
        // set RecyclerView
        setupExerciseStatusRecyclerView()

        Log.i("Intent >>", "setId: $mSetId exId : $mExerciseId")

        setupActionBar()

    }

    private fun getRowsData() : ArrayList<WorkoutModel>? {
        /*** WORKOUT LIST* */
        Constants.getWorkoutItems(this).filter { it.id == mSetId }.forEach { selectedSet ->
            mSetId = selectedSet.id
            mSetName = selectedSet.setName
            mWorkoutListItems = selectedSet.workouts!!
        }
        return  mWorkoutListItems
    }

    private fun getCurrentExercisePosition(): Int {
        if (mExerciseId != "") {
            for ((i, item) in mWorkoutListItems!!.withIndex()) {
                if (item.id == mExerciseId) {
                    currentExercisePosition = i - 1
                    return  currentExercisePosition
                }
            }
        }
        return currentExercisePosition // -1
    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        if (exerciseProgress != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
            pauseOffset = 0
        }

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if (player != null) {
            player!!.stop()
        }
        super.onDestroy()
    }

    /**
     * RestProgress
     */
    private fun setRestProgressBar() {
        binding.progressBar.progress = restProgress
        // rest minutes
        restTimerDuration = mWorkoutListItems!![currentExercisePosition+1].restTime
        //Log.i("Rest Time >>", "$restTimerDuration   $currentExercisePosition")
        restTimer = object : CountDownTimer((restTimerDuration * 1000).toLong(),1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = restTimerDuration - restProgress
                binding.tvTimer.text = (restTimerDuration - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                // progress icon
                mWorkoutListItems!![currentExercisePosition].setIsSelected(true) // Current Item is selected
                exerciseAdapter!!.notifyDataSetChanged() // Notified the current item to adapter class to reflect it into UI.

                setupExerciseView()
            }
        }.start()
    }

    private fun setupRestView(){
        binding.llRestView.visibility = View.VISIBLE
        binding.llExerciseView.visibility = View.GONE

        // Play Sound
        try {
            /*val soundURI =
            Uri.parse("android.resource://com.sevenminuteworkout/" + R.raw.press_start)
        player = MediaPlayer.create(applicationContext, soundURI)*/
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        }catch (e: Exception) {
            e.printStackTrace()
        }

        // reset values
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        binding.tvUpcomingExerciseName.text = mWorkoutListItems!![currentExercisePosition+1].name
        setRestProgressBar()

    }

    /**
     * ExerciseProgress
     */
    // Exercise Timer
    private fun setExerciseProgressBar(pauseOffsetL: Long) {
        binding.exerciseProgressBar.progress = exerciseProgress
        exerciseTimerDuration = mWorkoutListItems!![currentExercisePosition].workoutTime

        val newTimerDuration = (exerciseTimerDuration * 1000) - pauseOffsetL
        exerciseTimer = object : CountDownTimer(newTimerDuration, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                pauseOffset = (exerciseTimerDuration * 1000) - millisUntilFinished
                binding.exerciseProgressBar.progress = exerciseTimerDuration - exerciseProgress
                binding.exerciseTvTimer.text = (exerciseTimerDuration - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < mWorkoutListItems!!.size - 1) {
                    //set Green background for the completed exercise
                    mWorkoutListItems!![currentExercisePosition].setIsSelected(false)
                    mWorkoutListItems!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()

                    setupRestView()
                } else {
                    Toast.makeText(this@WorkoutActivity, "Great Job, you have completed",Toast.LENGTH_SHORT).show()
                    finish()
                    val intent = Intent(this@WorkoutActivity, FinishActivity::class.java)
                    intent.putExtra(Constants.INTENT_WORKOUT_SET_NAME, mSetName)
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun setupExerciseView(){

        binding.llRestView.visibility = View.GONE
        binding.llExerciseView.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
            pauseOffset = 0
        }
        speakOut(mWorkoutListItems!![currentExercisePosition].name)

        setExerciseProgressBar(pauseOffset)

        binding.ivImage.setImageResource(mWorkoutListItems!![currentExercisePosition].imageName)
        binding.exerciseTvName.text = mWorkoutListItems!![currentExercisePosition].name

    }

    /**
     * RecyclerView
     * refer to file activity_exercise.xml
     */

    private fun setupExerciseStatusRecyclerView(){
        binding.rvExerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = WorkoutStatusAdapter(mWorkoutListItems,this@WorkoutActivity )
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

    /**
     * PauseTimer
     */
    private fun pauseExerciseTimer(){
        if (exerciseTimer != null) {
            pauseOffset += 1000
            exerciseTimer!!.cancel()
        }
    }

    private fun checkPauseTimer(){
        if (isTimerActive) {
            pauseExerciseTimer()
        } else {
            setExerciseProgressBar(pauseOffset)
        }
        isTimerActive = !isTimerActive
        Toast.makeText(this, "Pause  ", Toast.LENGTH_SHORT).show()
    }



    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCustom)
        binding.tvTitle.text = mSetName //resources.getString(R.string.toolbar_workout)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_topbar_back_arrow)
        }
        binding.toolbarCustom.setNavigationOnClickListener {
            customDialogForBackButton()
        }
    }
}

 