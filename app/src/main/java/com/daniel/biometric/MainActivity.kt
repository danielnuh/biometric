package com.daniel.biometric

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daniel.biometric.databinding.ActivityMainBinding
import com.daniel.biometric.session.Session

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.apply {
            val session = Session(this@MainActivity)

            usernameTv.text = session.getUser().username

            logoutBtn.setOnClickListener {
                session.clear()

                Intent(this@MainActivity, LoginActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
        }
    }
}