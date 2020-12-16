package ru.trinitydigital.search

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.trinitydigital.search.di.appModules

class SearchApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, appModules)
    }
}