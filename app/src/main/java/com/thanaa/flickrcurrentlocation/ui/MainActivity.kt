package com.thanaa.flickrcurrentlocation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thanaa.flickrcurrentlocation.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        //remove the shadow in Navigation Bottom
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        val navController: NavController =
            Navigation.findNavController(this, R.id.fragment_container)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

//        bottomNavigationView.setupWithNavController(findNavController(R.id.fragment_container))

    }



}