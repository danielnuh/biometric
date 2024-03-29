package com.daniel.biometric

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.daniel.biometric.session.Session

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val session = Session(this)
            if (session.getUser().username.isEmpty()) {
                Intent(this@SplashScreenActivity, LoginActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            } else {
                Intent(this@SplashScreenActivity, MainActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
        }, 1000)
    }
}