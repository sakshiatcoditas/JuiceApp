package com.example.juice.di

import android.content.Context
import androidx.room.Room
import com.example.juice.data.AppDatabase
import com.example.juice.data.RoomJuiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "juice_database"
        ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: AppDatabase): RoomJuiceRepository {
        return RoomJuiceRepository(db.juiceDao())
    }
}
