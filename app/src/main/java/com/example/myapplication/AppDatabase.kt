package com.example.myapplication

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bottle::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getBottleDao(): BottleDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: android.content.Context): AppDatabase {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room
                        .databaseBuilder(context.applicationContext,
                            AppDatabase::class.java,
                            "db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}