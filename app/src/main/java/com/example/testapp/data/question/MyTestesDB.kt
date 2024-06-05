package com.example.testapp.data.question

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TestEntity::class], version = 4, exportSchema = false)
@TypeConverters(ListStringConverter::class)
abstract class MyTestesDB : RoomDatabase() {
    abstract fun testDao(): TestDao
    companion object {
        private var instance: MyTestesDB? = null

        @Synchronized
        fun getInstance(context: Context): MyTestesDB {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyTestesDB::class.java, "my-testes-database"
                ).fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }
}

