package com.example.m11_timer_data_storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

private const val PREFERENCE_NAME = "preference name"
private const val SHARED_PREFS_NAME = "shared_prefs_name"

class Repository(private val context: Context) {

    private var localVariable: String? = null

    private fun getDataFromSharedPreference(): String? {
        val arg = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return arg.getString(SHARED_PREFS_NAME, null)
    }

    private fun getDataFromLocalVariable(): String? = localVariable

    fun saveText(text: String) {
        localVariable += text
        val arg = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = arg.edit()
        editor.putString(SHARED_PREFS_NAME, text)
        editor.apply()
    }

    fun clearText() {
        localVariable = null
        val arg = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = arg.edit()
        editor.clear()
        editor.apply()
    }

    fun getText(): String? {
        return when {
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            getDataFromSharedPreference() != null -> getDataFromSharedPreference()!!
            else -> null
        }
    }
}