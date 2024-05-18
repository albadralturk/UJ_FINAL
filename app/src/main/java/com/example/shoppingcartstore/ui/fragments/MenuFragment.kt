package com.example.shoppingcartstore.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.shoppingcartstore.databinding.FragmentMenuBinding
import com.example.shoppingcartstore.ui.activity.EditProfileActivity
import com.example.shoppingcartstore.ui.activity.ListActivity
import com.example.shoppingcartstore.ui.activity.LoginActivity
import com.example.shoppingcartstore.ui.activity.MainActivity
import com.example.shoppingcartstore.utils.LOGOUT_PREF_KEY
import com.example.shoppingcartstore.utils.LOG_IN_PREF_KEY
import com.example.shoppingcartstore.utils.MySharedPreferences
import com.example.shoppingcartstore.utils.Utils.isDarkThemeEnabled
import com.example.shoppingcartstore.utils.Utils.openEmailApp
import com.example.shoppingcartstore.utils.Utils.showAlertDialogWithOkButton
import com.google.firebase.auth.FirebaseAuth


class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private val sharedPref: MySharedPreferences by lazy {
        MySharedPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = FirebaseAuth.getInstance().currentUser

        with(binding) {
            tvEmailMenu.text=currentUser?.email.toString()
            tvNameMenu.text=currentUser?.displayName.toString()
            edtReceiptNumber.setOnClickListener {
                startActivity(Intent(activity, EditProfileActivity::class.java))
            }
            edtMyList.setOnClickListener {
                startActivity(Intent(activity, ListActivity::class.java))
            }
            edtMail.setOnClickListener {
                context?.openEmailApp("1845444@ujedu.sa")
            }
            edtMyReceipts.setOnClickListener {
                context?.showAlertDialogWithOkButton("About Us","Welcome to List It - your ultimate grocery management app! \n" +
                        "\n" +
                        "List It simplifies your grocery shopping experience, ensuring you never forget an item again. With intuitive features, you can effortlessly create, organize, and share your shopping lists with ease. Say goodbye to the hassle of paper lists and hello to streamlined shopping.\n" +
                        "\n" +
                        "Whether you're planning meals, tracking expenses, or simply restocking essentials, List It has you covered. Download now and revolutionize the way you shop!")
            }
            edtLogout.setOnClickListener {
                sharedPref.saveValue(LOGOUT_PREF_KEY,true)
                startActivity(Intent(requireActivity(),LoginActivity::class.java))
            }

            themeSwitch.isChecked=requireActivity().isDarkThemeEnabled()
            themeSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Dark theme
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    // Light theme
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                restartActivity()
            }
        }
    }

    private fun restartActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        activity?.finish()
    }
}