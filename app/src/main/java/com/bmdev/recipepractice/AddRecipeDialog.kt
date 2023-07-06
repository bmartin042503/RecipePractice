package com.bmdev.recipepractice

import android.util.Log
import androidx.compose.animation.core.SnapSpec
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.NumberFormatException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeDialog(
    state: RecipeState,
    addIngredients: SnapshotStateList<Ingredient>,
    onEvent: (RecipeEvent) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onEvent(RecipeEvent.HideDialog)
        },
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                text = stringResource(id = R.string.add_recipe),
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier.padding(bottom = 20.dp),
                    value = state.addRecipeName,
                    onValueChange = { newValue ->
                        onEvent(RecipeEvent.SetRecipeName(newValue))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.recipe_name)
                        )
                    }
                )
                OutlinedTextField(
                    modifier = Modifier.padding(bottom = 30.dp),
                    value = state.addRecipeDescription,
                    onValueChange = { newValue ->
                        onEvent(RecipeEvent.SetRecipeDescription(newValue))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.recipe_description)
                        )
                    }
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = stringResource(id = R.string.add_ingredients),
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp
                )
                LazyColumn() {
                    if(addIngredients.size == 0) {
                        addIngredients.add(
                            Ingredient(
                                name = "",
                                unit = "",
                                count = 0
                            )
                        )
                    }
                    items(addIngredients.size) { index ->
                        AddIngredientRow(
                            index = index,
                            onEvent = onEvent,
                            addIngredients = addIngredients,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onEvent(RecipeEvent.AddRecipe)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.save)
                )
            }
        },
        dismissButton = {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    addIngredients.add(
                        Ingredient(
                            name = "",
                            unit = "",
                            count = 0
                        )
                    )
                }
            ) {
                Text(
                    text = stringResource(R.string.add_ingredient)
                )
            }
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngredientRow(
    addIngredients: SnapshotStateList<Ingredient>,
    index: Int,
    onEvent: (RecipeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(0.25f),
            value = addIngredients[index].count.toString(),
            onValueChange = { newValue ->
                try {
                    onEvent(RecipeEvent.SetIngredientCount(index, newValue.toInt()))
                } catch(e : NumberFormatException) {
                    Log.e("AddRecipeDialog", e.message.toString())
                }
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.ingredient_count),
                    maxLines = 1
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1
        )
        OutlinedTextField(
            modifier = Modifier.weight(0.25f),
            value = addIngredients[index].unit,
            onValueChange = { newValue ->
                onEvent(RecipeEvent.SetIngredientUnit(index, newValue))
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.ingredient_unit),
                    maxLines = 1
                )
            },
            maxLines = 1
        )
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = addIngredients[index].name,
            onValueChange = { newValue ->
                onEvent(RecipeEvent.SetIngredientName(index, newValue))
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.ingredient_name),
                    maxLines = 1
                )
            },
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecipeDialogPreview() {
    AddRecipeDialog(
        state = RecipeState(),
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