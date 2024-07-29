package com.opensource.getemotion.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.opensource.getemotion.databinding.RecyclerItemStarBinding
import com.opensource.getemotion.holders.ConfidenceStarsViewHolder

class ConfidenceStarsAdapter(private var data: MutableList<Boolean>): RecyclerView.Adapter<ConfidenceStarsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfidenceStarsViewHolder {
        val itemViewBinding: RecyclerItemStarBinding = RecyclerItemStarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ConfidenceStarsViewHolder(itemViewBinding)
    }



    override fun onBindViewHolder(holder: ConfidenceStarsViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


}