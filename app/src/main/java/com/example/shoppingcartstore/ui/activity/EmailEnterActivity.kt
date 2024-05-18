package com.example.shoppingcartstore.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ActivityEmailEnterBinding
import com.example.shoppingcartstore.utils.Utils.ENTER_EMAIL_INTENT_KEY
import com.example.shoppingcartstore.utils.Utils.isDarkThemeEnabled
import com.example.shoppingcartstore.utils.Utils.isValidEmail
import com.example.shoppingcartstore.utils.Utils.showToast

class EmailEnterActivity : AppCompatActivity() {

    private val binding : ActivityEmailEnterBinding by lazy {
        ActivityEmailEnterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!isDarkThemeEnabled()) {
            binding.ccParentActivity.setBackgroundResource(R.drawable.bg_img)
        } else {
            binding.ccParentActivity.setBackgroundColor(Color.BLACK)
        }
        with(binding){
            btnNext.setOnClickListener {
                if (edtEmailEnter.text.toString().isValidEmail()) {
                    val intent = Intent(this@EmailEnterActivity, PasswordEnterActivity::class.java)
                    intent.putExtra(ENTER_EMAIL_INTENT_KEY, edtEmailEnter.text.toString())
                    startActivity(intent)
                }else{
                    showToast(getString(R.string.invalid_email_entered))
                }
            }
        }
    }
}