package com.example.messengerapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [MessageEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this)  {
                val instance = databaseBuilder(
                    context = context.applicationContext,
                    klass = AppDatabase::class.java,
                    name = "messenger_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}