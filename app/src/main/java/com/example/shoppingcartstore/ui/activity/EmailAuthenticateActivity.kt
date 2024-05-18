package com.example.shoppingcartstore.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingcartstore.databinding.ActivityEmailAuthenticateBinding

class EmailAuthenticateActivity : AppCompatActivity() {

    private val binding: ActivityEmailAuthenticateBinding by lazy {
        ActivityEmailAuthenticateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {

        }
    }
}