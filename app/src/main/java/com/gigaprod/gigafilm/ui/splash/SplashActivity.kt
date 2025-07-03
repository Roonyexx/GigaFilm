package com.gigaprod.gigafilm.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.api.ApiClient
import com.gigaprod.gigafilm.ui.auth.AuthActivity
import com.gigaprod.gigafilm.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        ApiClient.init(this)

        Handler(Looper.getMainLooper()).postDelayed({

            val token = getSharedPreferences("auth", MODE_PRIVATE).getString("token", null)
            if (token != null) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, AuthActivity::class.java))
            }
            finish()
        }, 1500)
    }
}