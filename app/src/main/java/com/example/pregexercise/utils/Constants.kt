package com.example.pregexercise.utils

import android.content.Context
import android.util.Log
import com.example.pregexercise.models.*
import com.google.gson.Gson
import java.io.IOException

object Constants {

    const val SPEECH_DELAY : Float = 0.7f
    const val INTENT_WORKOUT_SET_ID = "workout_set_id"
    const val INTENT_WORKOUT_SET_NAME = "workout_set_name"
    const val INTENT_WORKOUT_EXERCISE_ID = "workout_exercise_id"

    fun getWorkoutItems(context: Context) : ArrayList<WorkoutSetModel> {
        val workoutSetItems : ArrayList<WorkoutSetModel> = ArrayList()
        var workoutItems : ArrayList<WorkoutModel>

        var workoutSetItem: WorkoutSetModel?
        var workoutItem: WorkoutModel?

        var imageResource: Int

        try {
            val getStream = context.assets.open("exerciseSets.json")
            val result  = getStream.bufferedReader(Charsets.UTF_8)
            val responseData = Gson().fromJson(result, JsonFileData::class.java)

            for (rowItem in responseData.workoutSet!!) {
                Log.i("WorkoutSetName", rowItem.setName)
                workoutItems = ArrayList()
                 for (item in rowItem.workouts) {
                     Log.i("WorkoutItem", item.name)
                     imageResource = context.resources.getIdentifier(item.imageName, "drawable", context.packageName)

                     workoutItem = WorkoutModel(
                        item.id,
                        item.name,
                        imageResource,
                         item.workoutTime,
                         item.restTime,
                         false,
                         false
                    )
                     workoutItems.add(workoutItem)
                }
                /**
                 * Save  to  WorkoutSetModel
                 */
                imageResource = context.resources.getIdentifier(rowItem.setImageName, "drawable", context.packageName)

                workoutSetItem = WorkoutSetModel(
                    rowItem.id,
                    rowItem.setName,
                    imageResource,
                    rowItem.setDescription,
                    workoutItems
                )
                workoutSetItems.add(workoutSetItem)
            }
        } catch (e: IOException) {
            Log.i("Error >>", ">> Error read file")
            e.printStackTrace()
        }
        return workoutSetItems
    }
}
