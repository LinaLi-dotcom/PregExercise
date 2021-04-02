package com.example.pregexercise.models

/**
 * Models get data from Json
 * */

data class JsonFileData (
    var workoutSet: ArrayList<JsonWorkoutSetModel>?
)


data class JsonWorkoutSetModel (
    var id: String,
    var setName: String,
    var setImageName: String,
    var setDescription: String,
    var workouts: ArrayList<JsonWorkoutModel>
)

data class JsonWorkoutModel (
    var id: String,
    var name: String,
    var imageName: String,
    var workoutTime: Int,
    var restTime: Int
)

/**
 * Models use in the app
 * */

data class WorkoutSetModel (
    var id: String,
    var setName: String,
    var setImageName: Int,
    var setDescription: String,
    var workouts: ArrayList<WorkoutModel>?
)

data class WorkoutModel (
    var id: String,
    var name: String,
    var imageName: Int,
    var workoutTime: Int,
    var restTime: Int,
    var isCompleted: Boolean,
    var isSelected: Boolean
){
    fun getIsCompleted(): Boolean {
        return isCompleted
    }
    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }

    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }
}



/*{
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getworkoutTime(): Int {
        return workoutTime
    }

    fun setworkoutTime(image: Int) {
        this.workoutTime = workoutTime
    }

    fun getImage(): Int {
        return image
    }

    fun setImage(image: Int) {
        this.image = image
    }

    fun getIsCompleted(): Boolean {
        return isCompleted
    }

    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }

    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }
}*/

/*{
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return setName
    }

    fun setName(name: String) {
        this.setName = name
    }

    fun getImage(): Int {
        return setImage
    }

    fun setImage(image: Int) {
        this.setImage = image
    }


    fun getWorkouts(): ArrayList<WorkoutModel> {
        return workouts
    }

    fun setWorkouts(workouts: ArrayList<WorkoutModel>) {
        this.workouts = workouts
    }
}*/