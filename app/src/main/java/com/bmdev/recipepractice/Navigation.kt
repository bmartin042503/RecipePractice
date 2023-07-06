package com.bmdev.recipepractice

sealed class Navigation(val route: String) {
    object RecipeScreen : Navigation("recipe_screen")
    object SelectedRecipeScreen : Navigation("selected_recipe_screen")
}
