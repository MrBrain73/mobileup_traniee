package com.example.mobileup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.mobileup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)

        val navHostFrag = supportFragmentManager.findFragmentById(R.id.mainContainer) as
                NavHostFragment
        val navController = navHostFrag.navController
        val config = AppBarConfiguration(navController.graph)

        binding.toolBar.setupWithNavController(navController, config)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.mainContainer)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}