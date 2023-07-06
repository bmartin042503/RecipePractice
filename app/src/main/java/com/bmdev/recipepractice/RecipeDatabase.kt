package com.bmdev.recipepractice

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Recipe::class, Ingredient::class],
    version = 1
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
    abstract val ingredientDao: IngredientDao
}