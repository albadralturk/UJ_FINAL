package com.example.shoppingcartstore.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ActivityLoginBinding
import com.example.shoppingcartstore.utils.LOGOUT_PREF_KEY
import com.example.shoppingcartstore.utils.LOG_IN_PREF_KEY
import com.example.shoppingcartstore.utils.MySharedPreferences
import com.example.shoppingcartstore.utils.Utils.isDarkThemeEnabled
import com.example.shoppingcartstore.utils.Utils.isValidEmail
import com.example.shoppingcartstore.utils.Utils.isValidPassword
import com.example.shoppingcartstore.utils.Utils.showToast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var firebaseAuth: FirebaseAuth
    private val sharedPref: MySharedPreferences by lazy {
        MySharedPreferences(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!isDarkThemeEnabled()) {
            binding.ccParentActivity.setBackgroundResource(R.drawable.bg_img)
        } else {
            binding.ccParentActivity.setBackgroundColor(Color.BLACK)
        }
        firebaseAuth = FirebaseAuth.getInstance()

        with(binding) {
            include.apply {
                ivBackPressTB.isVisible = false
                tvToolbarTitle.text = getString(R.string.app_name)
            }
            btnNext.setOnClickListener {
                val email = edtOnePassword.editText?.text.toString().trim()
                val confirmPassword = edtSecPassword.editText?.text.toString().trim()
                if (email.isEmpty() || confirmPassword.isEmpty()) {
                    showToast(getString(R.string.please_enter_all_fields))
                    return@setOnClickListener
                }
                if (!confirmPassword.isValidPassword()) {
                    showToast(getString(R.string.password_must_be_at_least_6_characters))
                    return@setOnClickListener
                }
                if (!email.isValidEmail()) {
                    showToast(getString(R.string.invalid_email_entered))
                    return@setOnClickListener
                }

                signUpWithEmailPassword(email = email, password = confirmPassword)
            }
        }
    }

    private fun signUpWithEmailPassword(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    sharedPref.saveValue(LOG_IN_PREF_KEY, true)
                    sharedPref.saveValue(LOGOUT_PREF_KEY, false)

                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    showToast(getString(R.string.entered_email_or_password_is_wrong))
                }
            }
    }
}