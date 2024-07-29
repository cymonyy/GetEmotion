package com.opensource.getemotion

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.opensource.getemotion.databinding.ActivityCreateAccountBinding
import com.opensource.getemotion.models.User

class CreateAccountActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {
    private lateinit var binding : ActivityCreateAccountBinding
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var userRef: CollectionReference = db.collection("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var error = false


        binding.btnProceed.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val sex = binding.tvSex.text.toString()
            val age = binding.etAge.text.toString()
            val dominantFinger = binding.tvFinger.text.toString()
            val dominantHand = binding.tvHand.text.toString()


            if(TextUtils.isEmpty(email)){
                binding.tvEmailLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.error_label))
                Toast.makeText(this@CreateAccountActivity, "Error! Please input your email.", Toast.LENGTH_SHORT).show()
                error = true
            }

            if(TextUtils.isEmpty(password)){
                binding.tvPassLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.error_label))
                Toast.makeText(this@CreateAccountActivity, "Error! Please input a password.", Toast.LENGTH_SHORT).show()
                error = true
            }
            else if(password.length < 8){
                binding.tvPassLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.error_label))
                Toast.makeText(this@CreateAccountActivity, "Error! Your password must have at least 8 characters.", Toast.LENGTH_SHORT).show()
                error = true
            }


            if(TextUtils.isEmpty(sex)){
                binding.tvSexLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.error_label))
                Toast.makeText(this@CreateAccountActivity, "Error! Please input your sex.", Toast.LENGTH_SHORT).show()
                error = true
            }

            if(TextUtils.isEmpty(age)){
                binding.tvAgeLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.error_label))
                Toast.makeText(this@CreateAccountActivity, "Error! Please input your age.", Toast.LENGTH_SHORT).show()
                error = true
            }
            else if(age.toInt() <= 0){
                binding.tvAgeLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.error_label))
                Toast.makeText(this@CreateAccountActivity, "Error! Please input your age.", Toast.LENGTH_SHORT).show()
                error = true
            }

            if(TextUtils.isEmpty(dominantFinger)){
                binding.tvFingerLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.error_label))
                Toast.makeText(this@CreateAccountActivity, "Error! Please input your dominant finger.", Toast.LENGTH_SHORT).show()
                error = true
            }

            if(TextUtils.isEmpty(dominantHand)){
                binding.tvHandLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.error_label))
                Toast.makeText(this@CreateAccountActivity, "Error! Please input your dominant hand.", Toast.LENGTH_SHORT).show()
                error = true
            }

            if(!error){
                registerUser(name, email, password, sex, age.toInt(), dominantFinger, dominantHand)


            }

        }

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            @SuppressLint("ResourceAsColor", "PrivateResource")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvEmailLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.fill_label))
                error = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            @SuppressLint("ResourceAsColor", "PrivateResource")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvPassLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.fill_label))
                error = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etAge.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            @SuppressLint("ResourceAsColor", "PrivateResource")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvAgeLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.fill_label))
                error = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.tvSex.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            @SuppressLint("ResourceAsColor", "PrivateResource")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvSexLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.fill_label))
                error = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.tvFinger.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            @SuppressLint("ResourceAsColor", "PrivateResource")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvFingerLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.fill_label))
                error = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.tvHand.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            @SuppressLint("ResourceAsColor", "PrivateResource")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvHandLabel.setTextColor(ContextCompat.getColor(this@CreateAccountActivity, R.color.fill_label))
                error = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        

        binding.tvLogin.setOnClickListener {
            val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    private fun registerUser(name: String, email: String, password: String, sex: String, age: Int, dominantFinger: String, dominantHand: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

            try {
                if (task.isSuccessful){
                    val user = User(name, email, password, sex, age, dominantFinger, dominantHand)
                    user.id = auth.currentUser?.uid.toString()

                    userRef.document(user.id).set(user)
                        .addOnSuccessListener {
                            Toast.makeText(this@CreateAccountActivity, "User Created Successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@CreateAccountActivity, HomepageActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }
                        .addOnFailureListener {
                            throw it
                        }
                }
                else throw task.exception!!

            } catch (e : Exception){
                Toast.makeText(this@CreateAccountActivity, e.message, Toast.LENGTH_LONG).show()
                finish()
            }


        }


    }

    fun showSexes(view : View) {
       val popup = PopupMenu(this@CreateAccountActivity, view)
        popup.setOnMenuItemClickListener(this@CreateAccountActivity)
        popup.inflate(R.menu.sexes)
        popup.show()
    }

    fun showFingers(view : View){
        val popup = PopupMenu(this@CreateAccountActivity, view)
        popup.setOnMenuItemClickListener(this@CreateAccountActivity)
        popup.inflate(R.menu.dominant_fingers)
        popup.show()
    }

    fun showHands(view : View){
        val popup = PopupMenu(this@CreateAccountActivity, view)
        popup.setOnMenuItemClickListener(this@CreateAccountActivity)
        popup.inflate(R.menu.dominant_hands)
        popup.show()
    }

    @SuppressLint("SetTextI18n")
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item != null) {
            return when(item.itemId){
                R.id.male -> {
                    binding.tvSex.text = "Male"
                    true
                }

                R.id.female -> {
                    binding.tvSex.text = "Female"
                    true
                }

                R.id.little_finger -> {
                    binding.tvFinger.text = "Little"
                    true
                }
                R.id.ring_finger -> {
                    binding.tvFinger.text = "Ring"
                    true
                }
                R.id.middle_finger -> {
                    binding.tvFinger.text = "Middle"
                    true
                }
                R.id.index_finger -> {
                    binding.tvFinger.text = "Index"
                    true
                }
                R.id.thumb_finger -> {
                    binding.tvFinger.text = "Thumb"
                    true
                }

                R.id.left_hand -> {
                    binding.tvHand.text = "Left"
                    true
                }

                R.id.right_hand -> {
                    binding.tvHand.text = "Right"
                    true
                }

                else -> false
            }
        }
        else return false
    }

}