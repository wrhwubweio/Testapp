package com.example.testapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [QuestionEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListStringConverter::class)
abstract class QuestionDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    companion object {
        private var instance: QuestionDatabase? = null

        @Synchronized
        fun getInstance(context: Context): QuestionDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuestionDatabase::class.java, "question-database"
                ).build()
            }
            return instance!!
        }
    }
}

