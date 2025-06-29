package com.gigaprod.gigafilm.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.ui.auth.LoginFragment
import com.gigaprod.gigafilm.ui.auth.RegisterFragment

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
