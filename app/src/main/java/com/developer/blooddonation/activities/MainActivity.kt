package com.developer.blooddonation.activities


import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.developer.blooddonation.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.ibrahimsn.lib.SmoothBottomBar


class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: SmoothBottomBar
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomBar)
        navController = findNavController(R.id.fragmentContainerView4)

        setupSmoothBottomMenu()
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav)
        popupMenu.menu.getItem(2).isVisible=false
        val menu = popupMenu.menu

        bottomNavigationView.setupWithNavController(menu, navController)
    }
}