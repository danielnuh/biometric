package com.daniel.biometric.session

import android.content.Context
import android.content.SharedPreferences
import com.daniel.biometric.model.User


class Session constructor(
    context: Context
) {
    private var sharedPreferences:SharedPreferences
    private var edit:SharedPreferences.Editor
    init {
        sharedPreferences = context.getSharedPreferences("biometric", Context.MODE_PRIVATE)
        edit = sharedPreferences.edit()
    }

    fun setUser(user:User){
        user.apply {
            edit.apply {
                putString("username", username).commit()
                putString("password", password).commit()
            }

        }
    }

    fun getUser():User{
        return User(
            sharedPreferences.getString("username", "").orEmpty(),
            sharedPreferences.getString("password", "").orEmpty()
        )
    }

    fun clear(){
        sharedPreferences.edit().clear().apply()
    }
}