package com.example.pregexercise.ui.adepters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pregexercise.databinding.ItemListWorkoutSetBinding
import com.example.pregexercise.models.WorkoutSetModel
import com.example.pregexercise.ui.WorkoutActivity
import com.example.pregexercise.ui.WorkoutListActivity
import com.example.pregexercise.utils.Constants
import com.example.pregexercise.utils.GlobalFunctions


class WorkoutSetAdapter(val context: Context,
                        private val items: ArrayList<WorkoutSetModel>
): RecyclerView.Adapter<WorkoutSetAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding =
            ItemListWorkoutSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetViewBindingHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: WorkoutSetModel = items[position]
        holder.bind(rowBinding)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(
        val context: Context,
        private val itemBinding: ItemListWorkoutSetBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        // A global variable for OnClickListener interface.
        private var onClickListener: View.OnClickListener? = null

        fun bind(rowData: WorkoutSetModel) {
            itemBinding.tvWorkoutSetTitle.text = rowData.setName
            itemBinding.tvWorkoutSetDescription.text = rowData.setDescription
            GlobalFunctions(context).loadPictureFromDrawableId(rowData.setImageName, itemBinding.ivWorkoutSet)

            itemBinding.llWorkoutSet.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(itemBinding.root)
                }
                val intent = Intent(context, WorkoutListActivity::class.java)
                intent.putExtra(Constants.INTENT_WORKOUT_SET_ID, rowData.id)
                intent.putExtra(Constants.INTENT_WORKOUT_SET_NAME, rowData.setName)
                context.startActivity(intent)
            }
        }
    }
}
