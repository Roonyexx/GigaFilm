package com.gigaprod.gigafilm.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gigaprod.gigafilm.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.authFragmentContainer, LoginFragment())
                .commit()
        }
    }

    fun showRegisterFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.authFragmentContainer, RegisterFragment())
            .addToBackStack(null)
            .commit()
    }

    fun showLoginFragment() {
        supportFragmentManager.popBackStack()
    }
}