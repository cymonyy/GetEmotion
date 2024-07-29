package com.opensource.getemotion.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.opensource.getemotion.R
import com.opensource.getemotion.databinding.DialogRateInteractionBinding

class RateInteractionDialog (context: Context) : Dialog(context) {

    private lateinit var binding : DialogRateInteractionBinding
    private var mListener: DialogListener? = null
    private var rating = 0

    interface DialogListener {
        fun onDataSent(clipID : String, rating : Int)
    }
    fun setBottomSheetListener(listener: DialogListener) {
        mListener = listener
    }




    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogRateInteractionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnConfirmInteraction.setOnClickListener {
            val number = binding.etClipID.text.toString()
            val clipID = binding.tvClipPrefix.text.toString() + number.padStart(4, '0')
            mListener?.onDataSent(clipID, rating)
            dismiss()
        }

        binding.etClipID.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            @SuppressLint("ResourceAsColor", "PrivateResource")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty() or s.isNullOrBlank()) {
                    binding.btnConfirmInteraction.isEnabled = false
                    binding.btnConfirmInteraction.setTextColor(context.getColor(R.color.recycler_item_interaction))
                    binding.btnConfirmInteraction.setBackgroundColor(context.getColor(R.color.fill_label))
                }
                else {
                    if(binding.rbRateConfidence.rating > 0){
                        binding.btnConfirmInteraction.isEnabled = true
                        binding.btnConfirmInteraction.setTextColor(context.getColor(R.color.white))
                        binding.btnConfirmInteraction.setBackgroundColor(context.getColor(R.color.confirm))
                    }

                }

            }
        })


        binding.rbRateConfidence.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating <= 0){
                binding.btnConfirmInteraction.isEnabled = false
                binding.btnConfirmInteraction.setTextColor(context.getColor(R.color.recycler_item_interaction))
                binding.btnConfirmInteraction.setBackgroundColor(context.getColor(R.color.fill_label))
            }

            else {
                if (!(binding.etClipID.text.isNullOrBlank() or binding.etClipID.text.isNullOrEmpty())) {
                    binding.btnConfirmInteraction.isEnabled = true
                    binding.btnConfirmInteraction.setTextColor(context.getColor(R.color.white))
                    binding.btnConfirmInteraction.setBackgroundColor(context.getColor(R.color.confirm))
                }

            }

            this.rating = rating.toInt()

            Log.d("RATE", rating.toString())
        }

    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)





}