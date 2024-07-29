package com.opensource.getemotion

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.opensource.getemotion.databinding.ActivityExperimentScreenBinding
import com.opensource.getemotion.dialogs.RateInteractionDialog
import com.opensource.getemotion.helpers.TimestampConverter
import com.opensource.getemotion.listeners.*
import com.opensource.getemotion.views.ExperimentView
import com.opensource.getemotion.listeners.OnDrawListener

class ExperimentActivity : AppCompatActivity() {

    private lateinit var binding : ActivityExperimentScreenBinding
    private lateinit var experimentView: ExperimentView
    private var user = Firebase.auth.currentUser!!
    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExperimentScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        experimentView = binding.incExperiment
        experimentView.setOnDrawListener(object : OnDrawListener {
            override fun onDrawStatusChanged(drawing: Boolean) {
                // Handle drawing status change
                if (drawing) {
                    binding.btnSubmit.isEnabled = true
                    binding.btnSubmit.setTextColor(resources.getColor(R.color.white, null))
                    binding.btnSubmit.setBackgroundColor(resources.getColor(R.color.confirm, null))
                }
            }
        })


        binding.btnSubmit.setOnClickListener {

            val confirmDialog = RateInteractionDialog(this@ExperimentActivity)
            confirmDialog.window?.setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.transparent, null)))
            confirmDialog.setCancelable(false)

            confirmDialog.setBottomSheetListener(object : RateInteractionDialog.DialogListener {
                override fun onDataSent(clipID : String, rating : Int) {
                    val intent = intent

                    for (i in 0 until experimentView.countInteraction){

                        val coordinates = hashMapOf(
                            "start" to hashMapOf(
                                "x" to experimentView.startCoordinates[i].first,
                                "y" to experimentView.startCoordinates[i].second,
                            ),
                            "end" to hashMapOf(
                                "x" to experimentView.endCoordinates[i].first,
                                "y" to experimentView.endCoordinates[i].second,
                            )
                        )

                        val velocity = hashMapOf(
                            "x" to hashMapOf(
                                "min" to experimentView.minVelocityXList[i],
                                "max" to experimentView.maxVelocityXList[i],
                                "mean" to experimentView.averageVelocityXList[i]
                            ),
                            "y" to hashMapOf(
                                "min" to experimentView.minVelocityYList[i],
                                "max" to experimentView.maxVelocityYList[i],
                                "mean" to experimentView.averageVelocityYList[i]
                            ),
                            "euclidean" to hashMapOf(
                                "min" to experimentView.minVelocityList[i],
                                "max" to experimentView.maxVelocityList[i],
                                "mean" to experimentView.averageVelocityList[i]
                            )
                        )

                        val acceleration = hashMapOf(
                            "x" to hashMapOf(
                                "min" to experimentView.minAccelerationXList[i],
                                "max" to experimentView.maxAccelerationXList[i],
                                "mean" to experimentView.averageAccelerationXList[i]
                            ),
                            "y" to hashMapOf(
                                "min" to experimentView.minAccelerationYList[i],
                                "max" to experimentView.maxAccelerationYList[i],
                                "mean" to experimentView.averageAccelerationYList[i]
                            ),
                            "euclidean" to hashMapOf(
                                "min" to experimentView.minAccelerationList[i],
                                "max" to experimentView.maxAccelerationList[i],
                                "mean" to experimentView.averageAccelerationList[i]
                            )
                        )

                        val data = hashMapOf(
                            "userID" to user.uid,
                            "clipID" to clipID,
                            "timestamp" to TimestampConverter.toTimeStamp(experimentView.timestamp[i]),
                            "coordinates" to coordinates,
                            "distance" to experimentView.distance[i],
                            "duration" to experimentView.duration[i],
                            "touchCount" to experimentView.touchCount[i],
                            "pressure" to experimentView.pressure[i],
                            "confidence" to rating,
                            "velocity" to velocity,
                            "acceleration" to acceleration
                        )

                        db.collection("Interactions")
                            .add(data)
                            .addOnSuccessListener { documentReference ->
                                Log.d("SUCCESSFUL ADD", "DocumentSnapshot added with ID: ${documentReference.id}")
                                Toast.makeText(this@ExperimentActivity, "The data from your interaction has been stored.", Toast.LENGTH_SHORT).show()
//
                            }
                            .addOnFailureListener { e ->
                                Log.w("FAILED ADD", "Error adding document", e)
                            }

                        intent.putExtra(ratingKey +"_${i}", rating)
                        intent.putExtra(clipIDKey +"_${i}", clipID)
                        intent.putExtra(timestampKey +"_${i}", experimentView.timestamp[i])
                    }

                    Log.d("CY_TIMESTAMP_RESULT_SIZE", experimentView.timestamp.size.toString())
                    Log.d("CY_TIMESTAMP_RESULT", TimestampConverter.toDateTime(experimentView.timestamp.last()))

                    Log.d("CY_START_RESULT_SIZE", experimentView.startCoordinates.size.toString())
                    Log.d("CY_START_RESULT", experimentView.startCoordinates.last().toString())

                    Log.d("CY_END_RESULT_SIZE", experimentView.endCoordinates.size.toString())
                    Log.d("CY_END_RESULT", experimentView.endCoordinates.last().toString())

                    Log.d("CY_DISTANCE_RESULT_SIZE", experimentView.distance.size.toString())
                    Log.d("CY_DISTANCE_RESULT", experimentView.distance.last().toString())

                    Log.d("CY_DURATION_RESULT_SIZE", experimentView.duration.size.toString())
                    Log.d("CY_DURATION_RESULT", experimentView.duration.last().toString())


                    Log.d("CY_VELOCITY_X_RESULT_SIZE", experimentView.averageVelocityXList.size.toString())
                    Log.d("CY_VELOCITY_X_RESULT", experimentView.averageVelocityXList.last().toString())

                    Log.d("CY_MIN_VELOCITY_X_RESULT_SIZE", experimentView.minVelocityXList.size.toString())
                    Log.d("CY_MIN_VELOCITY_X_RESULT", experimentView.minVelocityXList.last().toString())

                    Log.d("CY_MAX_VELOCITY_X_RESULT_SIZE", experimentView.maxVelocityXList.size.toString())
                    Log.d("CY_MAX_VELOCITY_X_RESULT", experimentView.maxVelocityXList.last().toString())

                    Log.d("CY_VELOCITY_Y_RESULT_SIZE", experimentView.averageVelocityYList.size.toString())
                    Log.d("CY_VELOCITY_Y_RESULT", experimentView.averageVelocityYList.last().toString())

                    Log.d("CY_MIN_VELOCITY_Y_RESULT_SIZE", experimentView.minVelocityYList.size.toString())
                    Log.d("CY_MIN_VELOCITY_Y_RESULT", experimentView.minVelocityYList.last().toString())

                    Log.d("CY_MAX_VELOCITY_Y_RESULT_SIZE", experimentView.maxVelocityYList.size.toString())
                    Log.d("CY_MAX_VELOCITY_Y_RESULT", experimentView.maxVelocityYList.last().toString())

                    Log.d("CY_VELOCITY_RESULT_SIZE", experimentView.averageVelocityList.size.toString())
                    Log.d("CY_VELOCITY_RESULT", experimentView.averageVelocityList.last().toString())


                    Log.d("CY_ACCEL_X_RESULT_SIZE", experimentView.averageAccelerationXList.size.toString())
                    Log.d("CY_ACCEL_X_RESULT", experimentView.averageAccelerationXList.last().toString())

                    Log.d("CY_MIN_ACCEL_X_RESULT_SIZE", experimentView.minAccelerationXList.size.toString())
                    Log.d("CY_MIN_ACCEL_X_RESULT", experimentView.minAccelerationXList.last().toString())

                    Log.d("CY_MAX_ACCEL_X_RESULT_SIZE", experimentView.maxAccelerationXList.size.toString())
                    Log.d("CY_MAX_ACCEL_X_RESULT", experimentView.maxAccelerationXList.last().toString())

                    Log.d("CY_ACCEL_Y_RESULT_SIZE", experimentView.averageAccelerationYList.size.toString())
                    Log.d("CY_ACCEL_Y_RESULT", experimentView.averageAccelerationYList.last().toString())

                    Log.d("CY_MIN_ACCEL_Y_RESULT_SIZE", experimentView.minAccelerationYList.size.toString())
                    Log.d("CY_MIN_ACCEL_Y_RESULT", experimentView.minAccelerationYList.last().toString())

                    Log.d("CY_MAX_ACCEL_Y_RESULT_SIZE", experimentView.maxAccelerationYList.size.toString())
                    Log.d("CY_MAX_ACCEL_Y_RESULT", experimentView.maxAccelerationYList.last().toString())

                    Log.d("CY_ACCEL_RESULT_SIZE", experimentView.averageAccelerationList.size.toString())
                    Log.d("CY_ACCEL_RESULT", experimentView.averageAccelerationList.last().toString())


                    Log.d("CY_TOUCH_RESULT_SIZE", experimentView.touchCount.size.toString())
                    Log.d("CY_TOUCH_RESULT", experimentView.touchCount.last().toString())

                    Log.d("CY_PRESSURE_RESULT_SIZE", experimentView.pressure.size.toString())
                    Log.d("CY_PRESSURE_RESULT", experimentView.pressure.last().toString())

//                    Log.d("START_RESULT_SIZE", experimentView.startCoordinates.size.toString())
                    Log.d("CY_CLIPID_RESULT", clipID)
                    Log.d("CY_CONFIDENCE_RESULT", rating.toString())

                    intent.putExtra(interactionCountKey, experimentView.countInteraction)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }

            })

            confirmDialog.show()

        }

    }

    companion object {
        const val interactionCountKey : String = "INTERACTION_COUNT_KEY"
        const val ratingKey : String = "CONFIDENCE_KEY"
        const val clipIDKey : String = "CLIP_ID_KEY"
        const val timestampKey : String = "TIMESTAMP_KEY"
    }

}