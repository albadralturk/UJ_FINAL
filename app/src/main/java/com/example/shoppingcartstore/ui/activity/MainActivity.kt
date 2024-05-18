package com.example.shoppingcartstore.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.ActivityMainBinding
import com.example.shoppingcartstore.ui.fragments.CategoryFragment
import com.example.shoppingcartstore.ui.fragments.HomeFragment
import com.example.shoppingcartstore.ui.fragments.MenuFragment
import com.example.shoppingcartstore.ui.fragments.ShopFragment
import com.example.shoppingcartstore.utils.Utils.isDarkThemeEnabled
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!isDarkThemeEnabled()) {
            binding.ccParentActivity.setBackgroundResource(R.drawable.bg_img)
        } else {
            binding.ccParentActivity.setBackgroundColor(Color.BLACK)
        }
        replaceFragment(HomeFragment())
        with(binding){
            bottomNavigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
                when(it.itemId){
                    R.id.home->{
                        replaceFragment(HomeFragment())
                    }
                    R.id.category->{
                        replaceFragment(CategoryFragment())
                    }
                    R.id.shop->{
                        replaceFragment(ShopFragment())
                    }
                    R.id.menu->{
                        replaceFragment(MenuFragment())
                    }
                }
                return@OnItemSelectedListener true
            })
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}