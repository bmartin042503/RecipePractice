package com.bmdev.recipepractice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectedRecipeScreen(
    state: RecipeState,
    onEvent: (RecipeEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.selectedRecipe?.let { recipe ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                text = recipe.name,
                fontSize = 38.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = recipe.description,
                fontSize = 18.sp
            )
            Divider(
                modifier = Modifier.padding(vertical = 26.dp)
            )
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stringResource(id = R.string.ingredients),
                fontSize = 22.sp
            )
            LazyColumn() {
                state.selectedRecipeIngredients?.let { ingredients ->
                    items(ingredients) { ingredient ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = ingredient.count.toString(),
                                fontSize = 18.sp
                            )
                            Text(
                                text = ingredient.unit.toString(),
                                fontSize = 18.sp
                            )
                            Text(
                                text = ingredient.name.toString(),
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
            Button(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .align(Alignment.End),
                onClick = {
                    onEvent(RecipeEvent.DeleteRecipe)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.delete_recipe)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectedRecipeScreenPreview() {
    SelectedRecipeScreen(state = RecipeState(
        selectedRecipe = Recipe(
            name = "RecipeName",
            description = "RecipeDescription RecipeDescription RecipeDescription RecipeDescription"
        ),
        selectedRecipeIngredients = listOf(
            Ingredient(
                name = "Ingredient 1",
                count = 10,
                unit = "g"
            ),
            Ingredient(
                name = "Ingredient 2",
                count = 10,
                unit = "g"
            ),
            Ingredient(
                name = "Ingredient 3",
                count = 10,
                unit = "g"
            ),
        )
    ), onEvent = {})
}