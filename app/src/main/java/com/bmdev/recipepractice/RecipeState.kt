package com.bmdev.recipepractice

data class RecipeState(
    val ingredients: List<Ingredient> = emptyList(),
    val isAddingRecipe: Boolean = false,
    val addRecipeName: String = "",
    val addRecipeDescription: String = "",
    // val addRecipeIngredients: MutableList<Ingredient> = mutableListOf(),
    val selectedRecipe: Recipe? = null,
    val selectedRecipeIngredients: List<Ingredient>? = null,
    val screen: Navigation = Navigation.RecipeScreen
)
