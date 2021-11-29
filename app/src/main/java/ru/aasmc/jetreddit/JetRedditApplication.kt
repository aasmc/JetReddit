package ru.aasmc.jetreddit

import android.app.Application
import ru.aasmc.jetreddit.di.DependencyInjector

class JetRedditApplication : Application() {

    lateinit var dependencyInjector: DependencyInjector

    override fun onCreate() {
        super.onCreate()
        initDependencyInjector()
    }

    private fun initDependencyInjector() {
        dependencyInjector = DependencyInjector(this)
    }

}