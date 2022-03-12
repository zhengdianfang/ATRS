package com.zhengdianfang.atrs

import android.app.Application

class AppApplication : Application() {
    companion object {
        lateinit var instance: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}