package com.bmdev.recipepractice

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val recipeDao: RecipeDao,
    private val ingredientDao: IngredientDao
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeState())
    val state = _state.asStateFlow()
    val recipes = recipeDao
        .getRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    var addIngredients = mutableStateListOf<Ingredient>()
        private set

    fun onEvent(event: RecipeEvent) {
        when(event) {
            is RecipeEvent.AddRecipe -> {
                val recipeName = state.value.addRecipeName
                val recipeDescription = state.value.addRecipeDescription
                val ingredients = addIngredients
                if(recipeName.isNotBlank() && recipeDescription.isNotBlank()
                    && ingredients.isNotEmpty()
                ) {
                    val upsertingRecipe = Recipe(
                        name = recipeName,
                        description = recipeDescription
                    )
                    viewModelScope.launch {
                        recipeDao.upsert(upsertingRecipe)
                        ingredients.forEach { ingredient ->
                            if(ingredient.name.isNotBlank() && ingredient.unit.isNotBlank()
                                && ingredient.count != 0) {
                                ingredientDao.upsert(ingredient)
                            }
                        }
                    }
                }
                _state.update {
                    it.copy(
                        isAddingRecipe = false,
                        addRecipeName = "",
                        addRecipeDescription = ""
                    )
                }
                addIngredients.clear()
            }
            is RecipeEvent.DeleteRecipe -> {
                state.value.selectedRecipe?.let { recipe ->
                    viewModelScope.launch {
                        recipeDao.delete(recipe)
                    }
                }
                _state.update {
                    it.copy(
                        selectedRecipe = null,
                        selectedRecipeIngredients = null,
                        screen = Navigation.RecipeScreen
                    )
                }
            }
            is RecipeEvent.SelectRecipe -> {
                viewModelScope.launch {
                    val selectedRecipe = recipeDao.getRecipeById(event.selectedRecipeId)
                    val selectedRecipeIngredients = ingredientDao.getIngredientsByRecipeId(event.selectedRecipeId)
                    _state.update {
                        it.copy(
                            selectedRecipe = selectedRecipe,
                            selectedRecipeIngredients = selectedRecipeIngredients,
                            screen = Navigation.SelectedRecipeScreen
                        )
                    }
                }
            }
            is RecipeEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingRecipe = true
                    )
                }
            }
            is RecipeEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingRecipe = false
                    )
                }
            }
            is RecipeEvent.AddIngredient -> {
                addIngredients.add(event.ingredient)
            }
            is RecipeEvent.SetRecipeDescription -> {
                _state.update {
                    it.copy(
                        addRecipeDescription = event.description
                    )
                }

            }
            is RecipeEvent.SetRecipeName -> {
                _state.update {
                    it.copy(
                        addRecipeName = event.name
                    )
                }
            }

            is RecipeEvent.SetIngredientCount -> {
                addIngredients[event.index].count = event.count
                Log.d("RecipeViewModel", "Ingredient count is changed to ${event.count}")
            }
            is RecipeEvent.SetIngredientName -> {
                addIngredients[event.index].name = event.name
                Log.d("RecipeViewModel", "Ingredient name is changed to ${event.name}")
            }
            is RecipeEvent.SetIngredientUnit -> {
                addIngredients[event.index].unit = event.unit
                Log.d("RecipeViewModel", "Ingredient unit is changed to ${event.unit}")
            }
        }
    }
}