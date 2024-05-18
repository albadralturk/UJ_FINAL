package com.example.shoppingcartstore.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ActivitySplashBinding
import com.example.shoppingcartstore.utils.LOGOUT_PREF_KEY
import com.example.shoppingcartstore.utils.LOG_IN_PREF_KEY
import com.example.shoppingcartstore.utils.MySharedPreferences
import com.example.shoppingcartstore.utils.Utils.isDarkThemeEnabled

class SplashActivity : AppCompatActivity() {

    private val sharedPreferences: MySharedPreferences by lazy {
        MySharedPreferences(this)
    }
    val binding:ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!isDarkThemeEnabled()) {
            binding.ccParentActivity.setBackgroundResource(R.drawable.bg_img)
        } else {
            binding.ccParentActivity.setBackgroundColor(Color.BLACK)
        }
        Handler(mainLooper).postDelayed({
            if (!sharedPreferences.getValue(LOGOUT_PREF_KEY,false)) {
                if (sharedPreferences.getValue(LOG_IN_PREF_KEY, false)) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, EmailEnterActivity::class.java))
                }
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2000)
    }
}