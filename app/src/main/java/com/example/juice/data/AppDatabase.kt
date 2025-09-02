package com.example.juice.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Juice::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun juiceDao(): JuiceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "juice_database"
                )
                    .fallbackToDestructiveMigration(false)
                .build()
                .also { INSTANCE = it }
            }
        }
    }
}
