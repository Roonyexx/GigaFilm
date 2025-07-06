package com.gigaprod.gigafilm.ui.main

import android.app.FragmentContainer
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.gigaprod.gigafilm.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        val fragmentContainer = findViewById<FrameLayout>(R.id.fragmentContainer)

        ViewCompat.setOnApplyWindowInsetsListener(fragmentContainer) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = systemBarsInsets.left,
                top = systemBarsInsets.top,
                right = systemBarsInsets.right,
                bottom = bottomNavigationView.height.coerceAtLeast(systemBarsInsets.bottom)
            )
            insets
        }


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MoviesFragment())
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_home -> MoviesFragment()
                //R.id.nav_search -> SearchFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> null
            }

            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, it)
                    .commit()
                true
            } ?: false
        }


    }
}
