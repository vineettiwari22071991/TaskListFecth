package com.example.tasklistfecth.core

import android.app.Application
import com.example.tasklistfecth.database.AppDatabase


//Core Application class
class TaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppDatabase.invoke(this)

    }

    companion object {
        lateinit var instance: TaskApplication
            private set
    }
}