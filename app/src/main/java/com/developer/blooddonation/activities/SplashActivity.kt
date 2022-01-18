package com.developer.blooddonation.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.developer.blooddonation.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_primary)
        setContentView(R.layout.activity_splash)

FirebaseMessaging.getInstance().subscribeToTopic("all")

        val user = Firebase.auth.currentUser
        if (user != null) {
            Handler(Looper.getMainLooper()).postDelayed({

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
               finish()
            }, 800)
        } else {

            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)

                finish()
            }, 800)
        }

    }
}