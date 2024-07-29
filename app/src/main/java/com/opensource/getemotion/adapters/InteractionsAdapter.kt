package com.opensource.getemotion.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.opensource.getemotion.databinding.RecyclerItemInteractionBinding
import com.opensource.getemotion.holders.InteractionsViewHolder
import com.opensource.getemotion.models.Interaction

class InteractionsAdapter(private var data: MutableList<Interaction>): RecyclerView.Adapter<InteractionsViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InteractionsViewHolder {
        val itemViewBinding: RecyclerItemInteractionBinding = RecyclerItemInteractionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return InteractionsViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: InteractionsViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: MutableList<Interaction>){
        data = newData
        notifyDataSetChanged()
    }

    fun addInteractionItem(item : Interaction) {
        this.data.add(item)
        notifyItemInserted(this.data.size - 1)
    }
}