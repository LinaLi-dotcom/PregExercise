package com.example.pregexercise.ui.adepters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        holder.bind(rowBinding,position)
    }

    override fun getItemCount(): Int = items.size

    class GetViewBindingHolder(
        val context: Context,
        private val itemBinding: ItemListHistoryBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(rowData: HistoryModel, position: Int) {
            itemBinding.tvPosition.text = (position + 1).toString()
            itemBinding.tvItem.text = rowData.completed_date

            // Updating the background color according to the odd/even positions in list.
            if (position % 2 == 0) {
                itemBinding.llHistoryItemMain.setBackgroundColor(
                    Color.parseColor("#EBEBEB")
                )
            } else {
                itemBinding.llHistoryItemMain.setBackgroundColor(
                    Color.parseColor("#FFFFFF")
                )
            }
        }
    }

}