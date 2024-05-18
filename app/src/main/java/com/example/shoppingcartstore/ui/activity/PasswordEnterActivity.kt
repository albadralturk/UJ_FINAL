package com.example.shoppingcartstore.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppingcartstore.databinding.ActivityPasswordEnterBinding
import com.example.shoppingcartstore.utils.Utils
import com.example.shoppingcartstore.utils.Utils.isValidPassword
import com.example.shoppingcartstore.utils.Utils.showToast
import com.google.firebase.auth.FirebaseAuth

class PasswordEnterActivity : AppCompatActivity() {

    private val binding:ActivityPasswordEnterBinding by lazy {
        ActivityPasswordEnterBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val email = intent.extras?.getString(Utils.ENTER_EMAIL_INTENT_KEY)
        with(binding){
            btnNext.setOnClickListener {
                val password = edtOnePassword.editText?.text.toString().trim()
                val confirmPassword = edtSecPassword.editText?.text.toString().trim()
                if (password.isEmpty() || confirmPassword.isEmpty()) {
                    showToast("Please enter all fields")
                    return@setOnClickListener
                }
                if (!password.isValidPassword()) {
                    showToast("Password must be at least 6 characters")
                    return@setOnClickListener
                }
                if (password != confirmPassword) {
                    showToast("Passwords do not match")
                    return@setOnClickListener
                }
                signUpWithEmailPassword(email!!, password)
            }
        }
    }

    private fun signUpWithEmailPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    showToast("Sign up successful")
                } else {
                    showToast("Authentication failed ${task.exception}")
                }
            }
    }
}