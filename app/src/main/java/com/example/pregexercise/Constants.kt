package com.example.pregexercise

class Constants {

    companion object {
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
                "exerciseTwo",
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

}