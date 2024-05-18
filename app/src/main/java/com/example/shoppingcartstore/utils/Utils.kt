package com.example.shoppingcartstore.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.shoppingcartstore.R
import com.example.shoppingcartstore.databinding.AddDescriptionViewBinding
import com.example.shoppingcartstore.ui.local.CategoryDataClass
import com.example.shoppingcartstore.ui.local.MainItemDataClass
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    var addedCategoryList: ArrayList<MainItemDataClass> = ArrayList()


    const val ENTER_EMAIL_INTENT_KEY = "EMAIL_INTENT"

    fun String.isValidEmail(): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return this.isNotEmpty() && this.matches(emailRegex.toRegex())
    }

    fun Context.showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun String.isValidPassword(): Boolean {
        return this.length >= 6
    }

    fun <T> Activity.openActivity(className: Class<T>) {
        startActivity(Intent(this, className::class.java))
    }

    fun Context.getCategoryItemList(category: String): ArrayList<CategoryDataClass> {
        val list: ArrayList<CategoryDataClass> = ArrayList()
        when (category) {
            "Fruits & Vegetables" -> {
                list.add(CategoryDataClass(R.drawable.category1_item1,
                   getString(R.string.banana)))
                list.add(CategoryDataClass(R.drawable.category2_item2, getString(R.string.orange)))
                list.add(CategoryDataClass(R.drawable.category3_item3, getString(R.string.mango)))
                list.add(CategoryDataClass(R.drawable.category4_item4, getString(R.string.peach)))
            }
            "Breakfast"->{
                list.add(CategoryDataClass(R.drawable.categoryy2_item1, getString(R.string.cereal)))
                list.add(CategoryDataClass(R.drawable.categoryy2_item2, getString(R.string.egg)))
            }
            "Meat & Fish"->{
                list.add(CategoryDataClass(R.drawable.categoryy4_item1, getString(R.string.fish)))
                list.add(CategoryDataClass(R.drawable.categoryy4_item2, getString(R.string.steak)))
            }
            "Snacks"->{
                list.add(CategoryDataClass(R.drawable.categoryy5_item1, getString(R.string.popcorn)))
                list.add(CategoryDataClass(R.drawable.categoryy5_item2, getString(R.string.chips)))
            }
            "Dairy"->{
                list.add(CategoryDataClass(R.drawable.categoryy3_item1, getString(R.string.cheese)))
                list.add(CategoryDataClass(R.drawable.categoryy3_item2, getString(R.string.milk)))
            }
            "Beverages"->{
                list.add(CategoryDataClass(R.drawable.categoryy6_item1,
                    getString(R.string.soft_drink)))
                list.add(CategoryDataClass(R.drawable.categoryy6_item2, getString(R.string.water)))
            }
        }
        return list
    }

    fun getCurrentTimeWithAmPm(): String {
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val currentTime = Date()
        return dateFormat.format(currentTime)
    }

    fun Activity.showDialog(callback: (String) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter List Title")
        val inputEditText = EditText(this)
        builder.setView(inputEditText)
        builder.setPositiveButton("OK") { _, _ ->
            val enteredText = inputEditText.text.toString()
            callback.invoke(enteredText)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun Activity.showDescriptionDialog(onDoneClick: ((String) -> Unit)) {
        val binding: AddDescriptionViewBinding =
            AddDescriptionViewBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
        builder.setView(binding.root)
        val dialog = builder.create()
        with(binding) {
            btnNext.setOnClickListener {
                onDoneClick.invoke(edDescription.text.toString())
                dialog.dismiss()
            }
            tvCancelDes.setOnClickListener {
                onDoneClick.invoke("")
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    fun showDeleteConfirmationDialog(context: Context, callback: () -> Unit) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.apply {
            setTitle("Delete")
            setMessage("Are you sure you want to delete the list?")
            setPositiveButton("Yes") { _, _ ->
                // Call the callback function when the "Yes" button is clicked
                callback.invoke()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog when "No" button is clicked
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun Context.openEmailApp(toEmail: String = "") {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data =
                Uri.parse("mailto:$toEmail")
        }
        startActivity(intent)
    }

    fun Context.showAlertDialogWithOkButton(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun Context.isDarkThemeEnabled(): Boolean {
        return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            else -> {
                // By default, return false
                false
            }
        }
    }


}