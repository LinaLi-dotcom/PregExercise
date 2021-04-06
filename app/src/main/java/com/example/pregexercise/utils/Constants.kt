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
                Log.i("WorkoutSetName >>", rowItem.setName)
                workoutItems = ArrayList()
                 for (item in rowItem.workouts) {
                    //Log.i("     restTime >>", item.restTime.toString())
                    /**
                     * Save workout to  WorkoutModel
                     */
                     //imageResource = context.resources.getIdentifier(item.imageName, "drawable", context.packageName)

                     imageResource = context.resources.getIdentifier("set01","drawable", context.packageName)

                     workoutItem = WorkoutModel(
                        item.id,
                        "test",//item.name,
                        imageResource,
                         item.workoutTime,
                         item.restTime,
                         false,
                         false
                    )
                     workoutItems.add(workoutItem)
                }
                /**
                 * Save Chapter to  WorkoutSetModel
                 */
                //imageResource = context.resources.getIdentifier(rowItem.setImageName, "drawable", context.packageName)
                imageResource = context.resources.getIdentifier("set01","drawable", context.packageName)

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

/*
* companion object {
        fun defaultExerciseList(): ArrayList<ExerciseModel>{
            val exerciseList = ArrayList<ExerciseModel>()

            val exerciseOne = ExerciseModel(1,
                "exerciseOne",
                R.drawable.ex001,
            false,
            false)
            exerciseList.add(exerciseOne)

            val exerciseTwo= ExerciseModel(2,
                "exerciseTwo",
                R.drawable.ex002,
                false,
                false)
            exerciseList.add(exerciseTwo)

            val exerciseThree = ExerciseModel(3,
                "exerciseThree",
                R.drawable.ex003,
                false,
                false)
            exerciseList.add(exerciseThree)

            val exerciseFour = ExerciseModel(4,
                "exerciseFour",
                R.drawable.ex004,
                false,
                false)
            exerciseList.add(exerciseFour)

            val exerciseFive = ExerciseModel(5,
                "exerciseFive",
                R.drawable.ex005,
                false,
                false)
            exerciseList.add(exerciseFive)

            val exerciseSix = ExerciseModel(6,
                "exerciseSix",
                R.drawable.ex006,
                false,
                false)
            exerciseList.add(exerciseSix)

            val exerciseSeven = ExerciseModel(7,
                "exerciseSeven",
                R.drawable.ex007,
                false,
                false)
            exerciseList.add(exerciseSeven)

            val exerciseEight = ExerciseModel(8,
                "exerciseEight",
                R.drawable.ex008,
                false,
                false)
            exerciseList.add(exerciseEight)


            return exerciseList
        }
    }

* */