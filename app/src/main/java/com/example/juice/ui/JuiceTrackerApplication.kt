package com.example.juice.ui

import android.app.Application
import com.example.juice.data.RoomJuiceRepository
import com.example.juice.data.AppDatabase

class JuiceTrackerApplication : Application() {
    val repository: RoomJuiceRepository by lazy {
        val database = AppDatabase.getDatabase(this)
        RoomJuiceRepository(database.juiceDao())
    }

}
