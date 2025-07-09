package com.gigaprod.gigafilm.ui.main

import android.app.FragmentContainer
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.gigaprod.gigafilm.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.ViewTreeObserver

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var moviesFragment: MoviesFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var searchFragment: SearchFragment
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        moviesFragment = MoviesFragment()
        profileFragment = ProfileFragment()
        searchFragment = SearchFragment()

        val fragmentContainer = findViewById<FrameLayout>(R.id.fragmentContainer)

        bottomNavigationView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                bottomNavigationView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val navHeight = bottomNavigationView.height

                ViewCompat.setOnApplyWindowInsetsListener(fragmentContainer) { view, insets ->
                    val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                    view.updatePadding(
                        left = systemBarsInsets.left,
                        top = systemBarsInsets.top,
                        right = systemBarsInsets.right,
                        bottom = navHeight.coerceAtLeast(systemBarsInsets.bottom)
                    )
                    insets
                }

                ViewCompat.requestApplyInsets(fragmentContainer)
            }
        })

        setupFragments()
        setupBottomNavigation()
    }

    private fun setupFragments() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, moviesFragment, "moviesFragment")
            .add(R.id.fragmentContainer, profileFragment, "profileFragment")
            .add(R.id.fragmentContainer,searchFragment, "searchFragment")
            .hide(profileFragment)
            .hide(searchFragment)
            .commit()

        activeFragment = moviesFragment
    }

    private fun setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> switchFragment(moviesFragment)
                R.id.nav_profile -> switchFragment(profileFragment)
                R.id.nav_search -> switchFragment(searchFragment)
            }
            true
        }
    }

    private fun switchFragment(target: Fragment) {
        if (activeFragment == target) return

        supportFragmentManager.beginTransaction()
            .hide(activeFragment!!)
            .show(target)
            .commit()

        activeFragment = target
    }
}
