package com.gigaprod.gigafilm.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gigaprod.gigafilm.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MoviesFragment())
                .commit()
        }
    }
}
