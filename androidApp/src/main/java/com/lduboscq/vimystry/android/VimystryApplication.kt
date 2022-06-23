package com.lduboscq.vimystry.android

import android.app.Application
import com.lduboscq.vimystry.android.di.appModule
import com.lduboscq.vimystry.di.initKoin

class VimystryApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            modules(appModule)
        }
    }
}