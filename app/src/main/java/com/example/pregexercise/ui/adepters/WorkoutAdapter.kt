package com.example.pregexercise.ui.adepters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pregexercise.databinding.ItemListWorkoutListBinding
import com.example.pregexercise.models.WorkoutModel
import com.example.pregexercise.ui.WorkoutActivity
import com.example.pregexercise.utils.Constants
import com.example.pregexercise.utils.GlobalFunctions


class WorkoutAdapter(val context: Context,
                     private val mSetId : String,
                     private val items: ArrayList<WorkoutModel>
): RecyclerView.Adapter<WorkoutAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding =
            ItemListWorkoutListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetViewBindingHolder(context, mSetId , itemBinding)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: WorkoutModel = items[position]
        holder.bind(rowBinding)

    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(
        val context: Context,
        private val mSetId: String,
        private val itemBinding: ItemListWorkoutListBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(rowData: WorkoutModel) {
            itemBinding.tvWorkoutListTitle.text = rowData.name
            itemBinding.tvTimer.text = rowData.workoutTime.toString() + " seconds"
            GlobalFunctions(context).loadPictureFromDrawableId(rowData.imageName, itemBinding.ivWorkoutList)

            itemBinding.llRow.setOnClickListener {
                // Intent to the other Activity
                val intent = Intent(context, WorkoutActivity::class.java)
                intent.putExtra(Constants.INTENT_WORKOUT_SET_ID, mSetId)
                intent.putExtra(Constants.INTENT_WORKOUT_EXERCISE_ID, rowData.id)
                context.startActivity(intent)
            }
        }
    }
}