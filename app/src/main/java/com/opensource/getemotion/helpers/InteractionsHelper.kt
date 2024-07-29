package com.opensource.getemotion.helpers

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.opensource.getemotion.models.Interaction
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class InteractionsHelper {

    private val _data = MutableLiveData<MutableList<Interaction>>()

    val data: LiveData<MutableList<Interaction>>
        get() = _data



    @OptIn(DelicateCoroutinesApi::class)
    fun getUserInteractions(userID: String){
        GlobalScope.launch(Dispatchers.Main) {
            try {

                val documents = withContext(Dispatchers.IO){
                    fetchData(userID)
                }

                if (documents.isEmpty()) throw Exception("No transactions found")
                processData(documents)


            } catch (e : Exception){
                Log.e("FATAL", e.stackTraceToString())
            }

        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun processData(documents: List<DocumentSnapshot>) {

        val data : MutableList<Interaction> = mutableListOf()

        for (document in documents){
            val new = document.toObject<Interaction>()
            if(new != null){
                data.add(new)
            }
        }

        _data.postValue(data)

    }

    private suspend fun fetchData(userID: String): MutableList<DocumentSnapshot> = withContext(Dispatchers.IO) {
        try{
            val db = FirebaseFirestore.getInstance()
            val querySnapshot = db.collection("Interactions")
                .whereEqualTo("userID", userID)
                .get()
                .await()
            return@withContext querySnapshot.documents

        }catch (e : Exception){
            Log.e("FATAL", e.stackTraceToString())
            return@withContext mutableListOf()
        }
    }


}