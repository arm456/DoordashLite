package com.example.doordashlite.dagger2

import android.content.Context
import com.example.doordashlite.ui.StoresFeedFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [NetworkModule::class, AndroidInjectionModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(storesFeedFragment: StoresFeedFragment)

}