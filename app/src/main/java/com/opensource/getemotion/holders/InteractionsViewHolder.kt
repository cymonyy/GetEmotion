package com.opensource.getemotion.holders

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.opensource.getemotion.databinding.RecyclerItemInteractionBinding
import com.opensource.getemotion.adapters.ConfidenceStarsAdapter
import com.opensource.getemotion.helpers.TimestampConverter
import com.opensource.getemotion.models.Interaction

class InteractionsViewHolder(private var binding: RecyclerItemInteractionBinding): RecyclerView.ViewHolder(binding.root){



    private lateinit var starsAdapter: ConfidenceStarsAdapter
    companion object{
        const val MAX_CONFIDENCE = 5
    }


    fun bindData(interaction: Interaction) {

        binding.tvDataclipID.text = interaction.clipID
        interaction.timestamp?.let { TimestampConverter.toDateTime(it) }
            ?.let { binding.tvDatetime.text = it }

        binding.rvConfidence.layoutManager = LinearLayoutManager(this.itemView.context, LinearLayoutManager.HORIZONTAL, false)

        val confidenceList = (0..<interaction.confidence).toList()
        starsAdapter = ConfidenceStarsAdapter(MutableList(MAX_CONFIDENCE) { index -> confidenceList.contains(index) })
        binding.rvConfidence.adapter = starsAdapter


        Log.e("INTERACTION", interaction.clipID)
        Log.e("INTERACTION", interaction.timestamp?.seconds.toString())
        interaction.timestamp?.let { TimestampConverter.toDateTime(it) }
            ?.let { Log.e("INTERACTION", it) }


        Log.e("INTERACTION", System.currentTimeMillis().toString())
        Log.e("INTERACTION", TimestampConverter.toDateTime(Timestamp(System.currentTimeMillis() / 1000, 0)))

        Log.e("INTERACTION", interaction.confidence.toString())
    }





}