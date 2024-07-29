package com.opensource.getemotion

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.opensource.getemotion.databinding.ActivityHomepageBinding
import com.opensource.getemotion.adapters.InteractionsAdapter
import com.opensource.getemotion.helpers.InteractionsHelper
import com.opensource.getemotion.helpers.TimestampConverter
import com.opensource.getemotion.models.Interaction

class HomepageActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding : ActivityHomepageBinding
    private var interactions : MutableList<Interaction> = mutableListOf()
    private val interactionsHelper = InteractionsHelper()
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : InteractionsAdapter
    private val handler = Handler(Looper.getMainLooper())

    private var user = Firebase.auth.currentUser!!

    private val viewNoteLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val result = result.data

            if(result != null){

                val count = result.getIntExtra(ExperimentActivity.interactionCountKey, -1)
                for (i in 0 until count){

                    adapter.addInteractionItem(
                        Interaction(
                        result.getStringExtra(ExperimentActivity.clipIDKey+"_${i}").toString(),
                        TimestampConverter.toTimeStamp(result.getLongExtra(ExperimentActivity.timestampKey+"_${i}", System.currentTimeMillis())),
                        result.getIntExtra(ExperimentActivity.ratingKey+"_${i}", 0)
                    )
                    )

                    // Do not modify anything below this statement.
                    binding.rvInteractions.smoothScrollToPosition(adapter.itemCount - 1)

                }


            }

        }
    }






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSignout.setOnClickListener {
            val intent = Intent(this@HomepageActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.srlSwipeLayout.setOnRefreshListener(this@HomepageActivity)

        recyclerView = this.binding.rvInteractions
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = InteractionsAdapter(mutableListOf())
        recyclerView.adapter = adapter

        interactionsHelper.data.observe(this, Observer { newData ->
            run {
                interactions = newData
                adapter.updateData(interactions)


//                Log.e("INTERACTION", interactions.first().dataclipID)
//                Log.e("INTERACTION", interactions.first().timestamp?.seconds.toString())
//                interactions.first().timestamp?.let { TimestampConverter.toDateTime(it) }
//                    ?.let { Log.e("INTERACTION", it) }
//
//
//                Log.e("INTERACTION", System.currentTimeMillis().toString())
//                Log.e("INTERACTION", TimestampConverter.toDateTime(Timestamp(System.currentTimeMillis() / 1000, 0)))
//
//                Log.e("INTERACTION", interactions.first().confidence.toString())
//                listAdapter.updateData(transactions)
            }
        })
        loadInteractions()


        binding.ibAddButton.setOnClickListener {
            val intent = Intent(this@HomepageActivity, ExperimentActivity::class.java)
            viewNoteLauncher.launch(intent)
        }

    }

    private fun loadInteractions() {
        val user = this.user.uid
        interactionsHelper.getUserInteractions(user)
    }

    override fun onRefresh() {
        binding.rvInteractions.visibility = View.GONE
        handler.postDelayed(
            {
                loadInteractions()
                binding.rvInteractions.visibility = View.VISIBLE
                binding.srlSwipeLayout.isRefreshing = false
        }, 1000)
    }

    override fun onDestroy() {
        // It's a good practice to remove callbacks in onDestroy to avoid memory leaks
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}