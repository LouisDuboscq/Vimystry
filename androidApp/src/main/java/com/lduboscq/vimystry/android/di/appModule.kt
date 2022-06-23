package com.lduboscq.vimystry.android.di

import com.lduboscq.vimystry.android.home.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(getPostsService = get()) }
}
