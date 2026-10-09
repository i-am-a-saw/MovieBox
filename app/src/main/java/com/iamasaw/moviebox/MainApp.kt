package com.iamasaw.moviebox

import android.app.Application
import com.iamasaw.moviebox.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApp)
            modules(AppModule)
        }
    }
}
