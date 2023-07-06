package com.bmdev.recipepractice

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Upsert
    suspend fun upsert(ingredient: Ingredient)

    @Delete
    suspend fun delete(ingredient: Ingredient)

    @Query("SELECT * FROM ingredients")
    fun getIngredients(): Flow<List<Ingredient>>

    @Query("SELECT * FROM ingredients WHERE recipe_id = :id")
    suspend fun getIngredientsByRecipeId(id: Int): List<Ingredient>
}