package com.opensource.getemotion

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.auth
import com.opensource.getemotion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val auth : FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //always sign out user at start
        auth.signOut()

        var error = false
        binding.btnLogin.setOnClickListener {

            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

            if(TextUtils.isEmpty(email)){
                binding.tvLoginEmailLabel.setTextColor(ContextCompat.getColor(this, R.color.error_label))
                Toast.makeText(this, "Error! Please input your email.", Toast.LENGTH_SHORT).show()
                error = true
            }

            if(TextUtils.isEmpty(password)){
                binding.tvLoginPasswordLabel.setTextColor(ContextCompat.getColor(this, R.color.error_label))
                Toast.makeText(this, "Error! Please input a password.", Toast.LENGTH_SHORT).show()
                error = true
            }
            else if(password.length < 8){
                binding.tvLoginPasswordLabel.setTextColor(ContextCompat.getColor(this, R.color.error_label))
                Toast.makeText(this, "Error! Your password must have at least 8 characters.", Toast.LENGTH_SHORT).show()
                error = true
            }

            if (!error){
                loginUser(email, password)
            }
        }

        binding.etLoginEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            @SuppressLint("ResourceAsColor", "PrivateResource")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvLoginEmailLabel.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.fill_label))
                error = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            @SuppressLint("ResourceAsColor", "PrivateResource")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvLoginPasswordLabel.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.fill_label))
                error = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })



        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }


    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            try {
                if (task.isSuccessful){
                    Toast.makeText(this, "User logged in successfully.", Toast.LENGTH_LONG).show()

                    //go to homepage
                    val intent = Intent(this, HomepageActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else throw task.exception!!

            }
            catch (e : FirebaseAuthInvalidCredentialsException){
                Log.e("FATAL", e.stackTraceToString())
                binding.tvLoginPasswordLabel.setTextColor(ContextCompat.getColor(this, R.color.error_label))
                Toast.makeText(this, "Error! Your password is incorrect.", Toast.LENGTH_LONG).show()
            }
            catch (e : FirebaseAuthInvalidUserException){
                Log.e("FATAL", e.stackTraceToString())
                binding.tvLoginEmailLabel.setTextColor(ContextCompat.getColor(this, R.color.error_label))
                Toast.makeText(this, "Error! Your email does not exist.", Toast.LENGTH_LONG).show()
            }
            catch (e : FirebaseTooManyRequestsException){
                Log.e("FATAL", e.stackTraceToString())
                Toast.makeText(this, "Error! Too many requests. Please try again after a five (5) minutes.", Toast.LENGTH_LONG).show()
            }
            catch (e : Exception){
                Log.e("FATAL", e.stackTraceToString())
                Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
            }

        }



    }
}