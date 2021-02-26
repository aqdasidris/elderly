package com.artsman.elderly

import android.app.Application
import com.artsman.elderly.core.DatabaseProvider

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseProvider.getInstance(this)

    }
}