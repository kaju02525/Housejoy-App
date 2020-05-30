package com.housejoy.di

import com.housejoy.mvvm.HouseListViewModel
import com.housejoy.network.RestClient
import com.housejoy.network.RestClient.webServices
import com.housejoy.repository.Repository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Repository(get()) }

}

val networkModule = module {
    single { RestClient }
    single { webServices() }
}

val viewModelModule = module {
    viewModel {
        HouseListViewModel(get())
    }
}