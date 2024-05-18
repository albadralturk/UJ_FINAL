package com.example.shoppingcartstore.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private val binding:ActivityListBinding by lazy {
        ActivityListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            includedToolbar.apply {
                tvToolbarTitle.text= getString(R.string.lists)
                ivBackPressTB.setOnClickListener { onBackPressed() }
            }
        }
    }
}