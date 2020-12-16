package ru.trinitydigital.search.di

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.trinitydigital.search.data.RetrofitBuilder
import ru.trinitydigital.search.ui.MainViewModel

val viewModelModule = module() {
    viewModel { MainViewModel(get()) }
}

val repositoryModule = module() {
    single { RetrofitBuilder.buildRetrofit() }
}


val appModules = listOf(viewModelModule, repositoryModule)