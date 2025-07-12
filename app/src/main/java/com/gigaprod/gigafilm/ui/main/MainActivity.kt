package com.gigaprod.gigafilm.ui.main

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
import com.gigaprod.gigafilm.network.ServerRepository

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var moviesFragment: MoviesFragment
    private var profileFragment: ProfileFragment? = null
    private var searchFragment: SearchFragment? = null
    private var activeFragment: Fragment? = null
    val serverRepository: ServerRepository = ServerRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        moviesFragment = MoviesFragment()

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

        setupInitialFragment()
        setupBottomNavigation()


    }

    private fun setupInitialFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, moviesFragment, "moviesFragment")
            .commit()
        activeFragment = moviesFragment
    }

    private fun setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> switchFragment(moviesFragment)
                R.id.nav_profile -> switchFragment(getProfileFragment())
                R.id.nav_search -> switchFragment(getSearchFragment())
            }
            true
        }
    }

    private fun getProfileFragment(): ProfileFragment {
        if (profileFragment == null) {
            profileFragment = ProfileFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, profileFragment!!, "profileFragment")
                .hide(profileFragment!!)
                .commit()
        }
        return profileFragment!!
    }

    private fun getSearchFragment(): SearchFragment {
        if (searchFragment == null) {
            searchFragment = SearchFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, searchFragment!!, "searchFragment")
                .hide(searchFragment!!)
                .commit()
        }
        return searchFragment!!
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