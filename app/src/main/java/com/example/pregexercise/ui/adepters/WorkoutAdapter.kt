package com.example.pregexercise.ui.adepters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pregexercise.databinding.ItemListWorkoutListBinding
import com.example.pregexercise.models.WorkoutModel
import com.example.pregexercise.utils.GlobalFunctions


class WorkoutAdapter(val context: Context,
                     private val items: ArrayList<WorkoutModel>
): RecyclerView.Adapter<WorkoutAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding =
            ItemListWorkoutListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetViewBindingHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: WorkoutModel = items[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(
        val context: Context,
        private val itemBinding: ItemListWorkoutListBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(rowData: WorkoutModel) {
            itemBinding.tvWorkoutListTitle.text = rowData.name
            itemBinding.tvTimer.text = rowData.workoutTime.toString()
            GlobalFunctions(context).loadPictureFromDrawableId(rowData.imageName, itemBinding.ivWorkoutList)
        }
    }
}