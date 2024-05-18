package com.example.shoppingcartstore.utils

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "MyAppPrefs"
    }

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    // Generic methods to save data
    fun <T> saveValue(key: String, value: T) {
        when (value) {
            is String -> editor.putString(key, value as String)
            is Int -> editor.putInt(key, value as Int)
            is Boolean -> editor.putBoolean(key, value as Boolean)
            // Add more cases for other data types as needed
        }
        editor.apply()
    }

    // Generic methods to retrieve data
    inline fun <reified T> getValue(key: String, defaultValue: T): T {
        return when (T::class) {
            String::class -> sharedPreferences.getString(key, defaultValue as String) as T
            Int::class -> sharedPreferences.getInt(key, defaultValue as Int) as T
            Boolean::class -> sharedPreferences.getBoolean(key, defaultValue as Boolean) as T
            // Add more cases for other data types as needed
            else -> defaultValue
        }
    }

    // Clear all data
    fun clearAllData() {
        editor.clear()
        editor.apply()
    }
}

