package com.udacity.shoestore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var model: MainViewModel
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProvider(this).get(MainViewModel::class.java)

        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        setUpNavigation()

        Timber.plant(Timber.DebugTree())
    }

    override fun onSupportNavigateUp() : Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    private fun setUpNavigation() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph, null)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }



}
