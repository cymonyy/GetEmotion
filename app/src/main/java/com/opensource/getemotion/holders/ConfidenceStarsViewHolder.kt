package com.opensource.getemotion.holders

import androidx.recyclerview.widget.RecyclerView
import com.opensource.getemotion.R
import com.opensource.getemotion.databinding.RecyclerItemStarBinding

class ConfidenceStarsViewHolder(private var binding: RecyclerItemStarBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(isFilled : Boolean){
        binding.ivStar.setImageResource(if(isFilled) R.drawable.star_filled else R.drawable.star)
    }

}