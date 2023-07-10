package com.example.testapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BootInfoDao::class], version = 2, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {

   abstract fun bootInfoDao(): BootInfoDao

   companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) = INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase ::class.java,
            "boot-info-db"
          ).build()
          INSTANCE = instance
          instance
        }
   }
}