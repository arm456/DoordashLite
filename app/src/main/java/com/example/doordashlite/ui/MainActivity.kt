package com.example.doordashlite.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.doordashlite.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val storesFeedFragment = StoresFeedFragment(R.layout.fragment_stores_feed)
            supportFragmentManager.beginTransaction().replace(R.id.container, storesFeedFragment).commit()
        }
    }
}