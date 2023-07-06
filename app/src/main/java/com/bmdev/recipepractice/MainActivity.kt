package com.bmdev.recipepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.bmdev.recipepractice.ui.theme.RecipePracticeTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            RecipeDatabase::class.java,
            "recipes.db"
        ).build()
    }

    private val viewModel by viewModels<RecipeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeViewModel(db.recipeDao, db.ingredientDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipePracticeTheme {
                val recipeState by viewModel.state.collectAsState()
                val recipesState by viewModel.recipes.collectAsState()
                val addIngredients = viewModel.addIngredients
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Navigation.RecipeScreen.route
                ) {
                    composable(
                        route = Navigation.RecipeScreen.route
                    ) {
                        RecipeScreen(
                            state = recipeState,
                            recipes = recipesState,
                            onEvent = viewModel::onEvent,
                            addIngredients = addIngredients
                        )
                    }
                    composable(
                        route = Navigation.SelectedRecipeScreen.route
                    ) {
                        SelectedRecipeScreen(
                            state = recipeState,
                            onEvent = viewModel::onEvent
                        )
                    }
                }
                if(recipeState.screen is Navigation.RecipeScreen) {
                    navController.navigate(
                        Navigation.RecipeScreen.route
                    )
                } else if(recipeState.screen is Navigation.SelectedRecipeScreen) {
                    navController.navigate(
                        Navigation.SelectedRecipeScreen.route
                    )
                }
            }
        }
    }
}