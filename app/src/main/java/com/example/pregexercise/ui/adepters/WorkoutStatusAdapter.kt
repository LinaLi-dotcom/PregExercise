package com.example.pregexercise.ui.adepters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pregexercise.R
import com.example.pregexercise.databinding.ItemListWorkoutStatusBinding
import com.example.pregexercise.models.WorkoutModel
import com.example.pregexercise.ui.WorkoutActivity
import com.example.pregexercise.utils.GlobalFunctions

class WorkoutStatusAdapter(private val items: ArrayList<WorkoutModel>?,
                           val context: WorkoutActivity
) : RecyclerView.Adapter<WorkoutStatusAdapter.GetViewBindingHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding =
            ItemListWorkoutStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetViewBindingHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: WorkoutModel = items!![position]
        holder.bind(rowBinding, position)
    }

    override fun getItemCount(): Int = items!!.size

    class GetViewBindingHolder(
        val context: WorkoutActivity,
        private val itemBinding: ItemListWorkoutStatusBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(rowData: WorkoutModel, position :Int) {

            itemBinding.tvItem.text = (position+1).toString()
            //GlobalFunctions(context).loadPictureFromDrawableId(rowData.imageName, itemBinding.ivWorkout)

            if (rowData.getIsSelected()){
                itemBinding.tvItem.background =
                    ContextCompat.getDrawable(
                        context, R.drawable.item_circular_color_accent_border
                    )
                itemBinding.tvItem.setTextColor(Color.parseColor("#212121"))
            }else if (rowData.getIsCompleted()){
                itemBinding.tvItem.background=
                    ContextCompat.getDrawable(context,
                        R.drawable.item_circular_color_accent_background
                    )
                itemBinding.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }else
            {
                itemBinding.tvItem.background=
                    ContextCompat.getDrawable(context,
                        R.drawable.item_circular_color_gray_background
                    )
                itemBinding.tvItem.setTextColor(Color.parseColor("#212121"))
            }

        }
    }
}