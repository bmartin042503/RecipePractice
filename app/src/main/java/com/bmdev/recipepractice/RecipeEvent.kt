package com.bmdev.recipepractice

sealed interface RecipeEvent {
    data class SelectRecipe(val selectedRecipeId: Int) : RecipeEvent
    data class SetRecipeName(val name: String) : RecipeEvent
    data class SetRecipeDescription(val description: String) : RecipeEvent
    data class SetIngredientName(val index: Int, val name: String) : RecipeEvent
    data class SetIngredientUnit(val index: Int, val unit: String) : RecipeEvent
    data class SetIngredientCount(val index: Int, val count: Int) : RecipeEvent
    data class AddIngredient(val ingredient: Ingredient) : RecipeEvent
    object AddRecipe : RecipeEvent
    object DeleteRecipe : RecipeEvent
    object ShowDialog : RecipeEvent
    object HideDialog : RecipeEvent
}