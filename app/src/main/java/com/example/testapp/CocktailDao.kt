package com.example.testapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import retrofit2.http.Query

@Dao
public interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(cocktail: CocktailEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktails(cocktails: List<CocktailEntity>): List<Long>

    @Query("SELECT * FROM cocktails")
    public suspend fun getCocktails(): List<CocktailEntity>

    @Query("DELETE FROM cocktails WHERE id = :cocktailId")
    public suspend fun deleteCocktailById(cocktailId: String)

    @Query("SELECT * FROM cocktails")
    public fun getAllCocktails(): List<CocktailEntity>

    @Query("SELECT COUNT(*) FROM cocktails")
    public fun getCocktailsCount(): Int // Метод для получения количества коктейлей в базе данных

    @Query("DELETE FROM cocktails")
    public fun deleteAllCocktails(): Int // Метод для удаления всех коктейлей из базы данных

    @Query("DELETE FROM cocktails WHERE id NOT IN (SELECT id FROM cocktails ORDER BY id DESC LIMIT 15)")
    public fun deleteExcessCocktails()

    @Delete
    fun deleteCocktails(cocktails: List<CocktailEntity>)
}