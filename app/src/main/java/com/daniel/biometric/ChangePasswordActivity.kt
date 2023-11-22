package com.daniel.biometric

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.daniel.biometric.databinding.ActivityChangePasswordBinding
import com.daniel.biometric.session.BiometricSession
import com.daniel.biometric.session.Session

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            changePasswordBtn.setOnClickListener {
                val password = binding.passwrodEt.text.toString()
                val confirmPassword = binding.confirmPasswrodEt.text.toString()

                if (password.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "password or confirm password kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }else if (password != confirmPassword){
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "password dan confirm password tidak sama",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    BiometricSession(this@ChangePasswordActivity).clear()
                    val session = Session(this@ChangePasswordActivity)
                    val user = session.getUser()
                    user.password = password
                    session.clear()
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "Berhasil mengubah password",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }


    }
}