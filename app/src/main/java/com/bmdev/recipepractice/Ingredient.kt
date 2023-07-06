package com.bmdev.recipepractice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredients",
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipe_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["recipe_id"])]
)
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("recipe_id")
    val recipeId: Int = 0,
    var name: String,
    var unit: String,
    var count: Int,

)
