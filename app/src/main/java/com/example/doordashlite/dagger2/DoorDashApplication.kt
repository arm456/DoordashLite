package com.example.doordashlite.dagger2

import android.app.Application

class DoorDashApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}