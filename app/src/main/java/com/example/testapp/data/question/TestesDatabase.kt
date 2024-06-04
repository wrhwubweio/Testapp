package com.example.testapp.data.question

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TestEntity::class], version = 10, exportSchema = false)
@TypeConverters(ListStringConverter::class)
abstract class TestDatabase : RoomDatabase() {
    abstract fun testDao(): TestDao
    companion object {
        private var instance: TestDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TestDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestDatabase::class.java, "test-database"
                ).fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }
}

