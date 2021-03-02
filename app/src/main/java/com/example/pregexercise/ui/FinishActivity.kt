package com.example.pregexercise.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.pregexercise.BaseActivity
import com.example.pregexercise.R
import com.example.pregexercise.databinding.ActivityFinishBinding
import com.example.pregexercise.models.SqliteOpenHelper
import com.example.pregexercise.models.WorkoutModel
import com.example.pregexercise.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : BaseActivity() {
    private lateinit var binding: ActivityFinishBinding

    private var mSetName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Constants.INTENT_WORKOUT_SET_NAME)) {
            mSetName = intent.getStringExtra(Constants.INTENT_WORKOUT_SET_NAME)!!
            binding.tvWorkoutSetTitle.text = resources.getString(R.string.finish_exercise_set).replace("##", mSetName)
        }
        binding.btnFinish.setOnClickListener {
            finish()
            startActivity(Intent(this@FinishActivity, WorkoutSetActivity::class.java))
        }

        addDateToDatabase()
        setFullScreen()
    }

    private fun addDateToDatabase() {
        val c = Calendar.getInstance() // Calender Current Instance
        val dateTime = c.time // Current Date and Time of the system.
        Log.e("Date : ", "" + dateTime) // Printed in the log.

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()) // Date Formatter
        val date = sdf.format(dateTime) // dateTime is formatted in the given format.
        Log.e("Formatted Date : ", "" + date) // Formatted date is printed in the log.

        // Instance of the Sqlite Open Helper class.
        val dbHandler = SqliteOpenHelper(this, null)
        dbHandler.addDate(mSetName,date) // Add date function is called.
     }
}