package com.thanaa.flickrcurrentlocation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thanaa.flickrcurrentlocation.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.background = null
        //remove the shadow in Navigation Bottom
        bottomNavigationView.background = null
        //made the buttons place holder for it to separate the icons and disable them
        bottomNavigationView.menu.getItem(2).isEnabled = false
        bottomNavigationView.menu.getItem(1).isEnabled = false

        val navController: NavController =
            Navigation.findNavController(this, R.id.fragment_container)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

//        bottomNavigationView.setupWithNavController(findNavController(R.id.fragment_container))

    }



}