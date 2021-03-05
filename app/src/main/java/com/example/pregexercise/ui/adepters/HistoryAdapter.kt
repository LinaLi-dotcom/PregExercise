package com.example.pregexercise.ui.adepters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pregexercise.R
import com.example.pregexercise.databinding.ItemListHistoryBinding
import com.example.pregexercise.models.HistoryModel

class HistoryAdapter(val context: Context,
                     private val items: ArrayList<HistoryModel>
): RecyclerView.Adapter<HistoryAdapter.GetViewBindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetViewBindingHolder {
        val itemBinding =
            ItemListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GetViewBindingHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: GetViewBindingHolder, position: Int) {
        val rowBinding: HistoryModel = items[position]
        holder.bind(rowBinding,items.size,position)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(
        val context: Context,
        private val itemBinding: ItemListHistoryBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("ResourceType")
        fun bind(rowData: HistoryModel, itemsSize: Int, position: Int) {
            itemBinding.tvPosition.text = (itemsSize - position).toString()
            itemBinding.tvSetName.text = rowData.set_name
            itemBinding.tvDate.text = rowData.completed_date
            // Updating the background color according to the odd/even positions in list.

            if (position % 2 == 0) {
                itemBinding.llHistoryItemMain.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.row_odd_background)
                )
            } else {
                itemBinding.llHistoryItemMain.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.row_even_background)
                )
            }
        }
    }

}