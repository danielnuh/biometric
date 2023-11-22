package com.daniel.biometric

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.daniel.biometric.databinding.ActivityLoginBinding
import com.daniel.biometric.model.User
import com.daniel.biometric.session.BiometricSession
import com.daniel.biometric.session.Session

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var biometricSession: BiometricSession
    private lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        biometricSession = BiometricSession(this@LoginActivity)
        session = Session(this@LoginActivity)

        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    loginRequest(biometricSession.getUser())
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })


        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()

        binding.apply {
            loginBtn.setOnClickListener {
                val username = usernameEt.text.toString()
                val password = passwrodEt.text.toString()

                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(
                        this@LoginActivity,
                        "username atau password kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    val user = User(
                        username, password
                    )
                    biometricSession.setUser(user)
                    loginRequest(user)
                }
            }

            fingerprintBtn.setOnClickListener {
                isSupportDevice {
                    toLogin {
                        biometricPrompt.authenticate(promptInfo)
                    }
                }
            }

            faceBtn.setOnClickListener {
                toLogin{
                    loginRequest(biometricSession.getUser())
                }
            }

            changePasswordBtn.setOnClickListener {
                Intent(this@LoginActivity, ChangePasswordActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }

    }

    private fun toLogin(result:() ->Unit) {
        if (biometricSession.getUser().username.isEmpty()){
            Toast.makeText(
                this@LoginActivity,
                "Lakukan login terlebidahulu",
                Toast.LENGTH_SHORT
            ).show()
        }else{
            result()
        }
    }

    private fun loginRequest(user:User) {
        session.setUser(user)
        Intent(this@LoginActivity, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
    private fun isSupportDevice(result:()->Unit){

        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                result()
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Toast.makeText(
                    this,
                    "No biometric features available on this device.",
                    Toast.LENGTH_SHORT
                ).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Toast.makeText(
                    this,
                    "Biometric features are currently unavailable.",
                    Toast.LENGTH_SHORT
                ).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
//                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
//                    putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
//                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
//                }
//                startActivityForResult(enrollIntent, REQUEST_CODE)
            }
        }
    }
}