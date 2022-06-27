package com.lduboscq.vimystry.di

import com.lduboscq.vimystry.domain.PostsService
import com.lduboscq.vimystry.platformModule
import com.lduboscq.vimystry.remote.PostsGraphQLApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(), platformModule())
    }

fun commonModule() = module {
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
    single<PostsService> { PostsGraphQLApi() }
}
