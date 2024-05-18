package com.example.shoppingcartstore.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ActivityEditProfileBinding
import com.example.shoppingcartstore.utils.Utils.isDarkThemeEnabled

class EditProfileActivity : AppCompatActivity() {

    private val binding:ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
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
            includedToolbar.apply {
                ivBackPressTB.setOnClickListener { onBackPressed() }
                tvToolbarTitle.text=getString(R.string.edit_profile)
            }
        }
    }
}