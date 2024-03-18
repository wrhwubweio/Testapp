package com.example.testapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CocktailEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListStringConverter::class)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
    companion object {
        private var instance: CocktailDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CocktailDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    CocktailDatabase::class.java, "cocktail-database"
                ).build()
            }
            return instance!!
        }
    }

}

