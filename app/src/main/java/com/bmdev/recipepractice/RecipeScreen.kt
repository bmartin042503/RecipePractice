package com.bmdev.recipepractice

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    state: RecipeState,
    recipes: List<Recipe>,
    onEvent: (RecipeEvent) -> Unit,
    addIngredients: SnapshotStateList<Ingredient>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(RecipeEvent.ShowDialog)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_recipe)
                )
            }
        }
    ) { paddingValues ->
        if(state.isAddingRecipe) {
            AddRecipeDialog(
                state = state,
                onEvent = onEvent,
                addIngredients = addIngredients
            )
            Log.d("RECIPESCREEN","AlertDialog is shown")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.recipes),
                    textAlign = TextAlign.Center,
                    fontSize = 34.sp
                )
            }
            items(recipes) { recipe ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier,
                            text = recipe.name,
                            fontSize = 20.sp
                        )
                        Text(
                            modifier = Modifier,
                            text = recipe.description,
                            fontSize = 16.sp,
                        )
                    }
                    IconButton(
                        onClick = {
                            onEvent(RecipeEvent.SelectRecipe(
                                recipe.id
                            ))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = stringResource(R.string.select_recipe)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreview() {
    RecipeScreen(
        state = RecipeState(),
        recipes = listOf(
            Recipe(1, "RECIPE 1", "DESCRIPTION"),
            Recipe(2, "RECIPE 2", "DESCRIPTION"),
            Recipe(3, "RECIPE 3", "DESCRIPTION"),
            Recipe(4, "RECIPE 4", "DESCRIPTION"),
            Recipe(5, "RECIPE 5", "DESCRIPTION"),
            Recipe(6, "RECIPE 6", "DESCRIPTION"),
            Recipe(7, "RECIPE 7", "DESCRIPTION"),
            Recipe(8, "RECIPE 8", "DESCRIPTION"),
            Recipe(9, "RECIPE 9", "DESCRIPTION"),
            Recipe(10, "RECIPE 10", "DESCRIPTION"),
            Recipe(11, "RECIPE 11", "DESCRIPTION"),
            Recipe(12, "RECIPE 12", "DESCRIPTION"),
            Recipe(13, "RECIPE 13", "DESCRIPTION"),
            Recipe(14, "RECIPE 14", "DESCRIPTION"),
            Recipe(15, "RECIPE 15", "DESCRIPTION")
        ),
        onEvent = {},
        addIngredients = remember {
            mutableStateListOf(
                Ingredient(
                    name = "Ingredient 1",
                    count = 10,
                    unit = "g"
                )
            )
        }
    )
}