package com.marymamani.aquariusapp.data.datasource.local

import android.content.SharedPreferences
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AuthLocalDataSource {

    companion object {
        private const val IS_USER_LOGGED_IN_KEY = "is_user_logged_in"
        private const val USER_TOKEN_KEY = "user_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val USER_NAME_KEY = "user_name"
    }

    override fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_USER_LOGGED_IN_KEY, false)
    }

    override fun saveUserToken(token: String, refreshToken: String, userName: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_TOKEN_KEY, token)
        editor.putString(REFRESH_TOKEN_KEY, refreshToken)
        editor.putBoolean(IS_USER_LOGGED_IN_KEY, true)
        editor.putString(USER_NAME_KEY, userName)
        editor.apply()
    }

    override fun clearUserData() {
        val editor = sharedPreferences.edit()
        editor.remove(USER_TOKEN_KEY)
        editor.remove(REFRESH_TOKEN_KEY)
        editor.remove(USER_NAME_KEY)
        editor.putBoolean(IS_USER_LOGGED_IN_KEY, false)
        editor.apply()
    }

    override fun getUserToken(): String? {
        return sharedPreferences.getString(USER_TOKEN_KEY, null)
    }

    override fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }

    override fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME_KEY, null)
    }
}