package com.daniel.biometric.session

import android.content.Context
import android.content.SharedPreferences
import com.daniel.biometric.model.User


class BiometricSession constructor(
    context: Context
) {
    private var sharedPreferences:SharedPreferences
    private var edit:SharedPreferences.Editor
    init {
        sharedPreferences = context.getSharedPreferences("biometricSession", Context.MODE_PRIVATE)
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
        edit.clear()
        edit.commit()
    }
}