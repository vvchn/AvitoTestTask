package com.vvchn.avitotesttask.presentation.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vvchn.avitotesttask.R
import com.vvchn.avitotesttask.presentation.Dimens
import com.vvchn.avitotesttask.presentation.navgraph.Route
import com.vvchn.avitotesttask.presentation.ui.theme.adjustButton
import com.vvchn.avitotesttask.presentation.ui.theme.searchBarColor
import com.vvchn.avitotesttask.presentation.ui.theme.topBarColor


@Composable
fun ChooseCountries(vm: MainScreenViewModel) {
    Surface(color = searchBarColor, modifier = Modifier.fillMaxSize()) {
        val lst = vm.getAllPossibleCountries()
        val res: MutableMap<String, Boolean> = mutableMapOf()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (lst.isEmpty()) {
                Text(text = stringResource(id = R.string.no_data), maxLines = 1)
            } else {
                lst.forEach { country ->
                    res[country.name!!] = false
                }
                LazyColumn {
                    items(lst.size) { country ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = lst[country].name!!)
                            Checkbox(
                                checked = res[lst[country].name]!!,
                                onCheckedChange = { }
                            )
                        }
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
        }
        Button(modifier = Modifier.fillMaxWidth(fraction = 0.8f), onClick = { }) {
            Text(
                text = stringResource(id = R.string.apply),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersBar(vm: MainScreenViewModel, navController: NavController) {

    val uiState by vm.state.collectAsStateWithLifecycle()

    Surface(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxSize()
            .background(searchBarColor),
        color = searchBarColor,
    ) {
        Column {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.filters),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "backToMainScreen",
                        tint = Color.White
                    )
                }
            }, colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = topBarColor))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.years),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )

                TextField(
                    modifier = Modifier
                        .width(Dimens.textFieldWidth)
                        .height(Dimens.textFieldHeight),
                    value = uiState.yearLeftBound,
                    placeholder = {
                        Text(text = "1800")
                    },
                    colors = TextFieldDefaults.colors().copy(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
                    onValueChange = vm::collectLeftBoundDate,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = stringResource(id = R.string.from)) },
                    maxLines = 1,
                    singleLine = true
                )

                TextField(
                    modifier = Modifier
                        .width(Dimens.textFieldWidth)
                        .height(Dimens.textFieldHeight),
                    value = uiState.yearRightBound,
                    placeholder = {
                        Text(text = "2024")
                    },
                    colors = TextFieldDefaults.colors().copy(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
                    onValueChange = vm::collectRightBoundDate,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = stringResource(id = R.string.to)) },
                    maxLines = 1,
                    singleLine = true
                )

            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.age),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )

                TextField(
                    modifier = Modifier
                        .width(Dimens.textFieldWidth)
                        .height(Dimens.textFieldHeight),
                    value = uiState.ageRatingLeftBound,
                    placeholder = {
                        Text(text = "0")
                    },
                    colors = TextFieldDefaults.colors().copy(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
                    onValueChange = vm::collectAgeRatingLeftBound,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = stringResource(id = R.string.from)) },
                    maxLines = 1,
                    singleLine = true
                )

                TextField(
                    modifier = Modifier
                        .width(Dimens.textFieldWidth)
                        .height(Dimens.textFieldHeight),
                    value = uiState.ageRatingRightBound,
                    placeholder = {
                        Text(text = "18")
                    },
                    colors = TextFieldDefaults.colors().copy(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
                    onValueChange = vm::collectAgeRatingRightBound,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = stringResource(id = R.string.to)) },
                    maxLines = 1,
                    singleLine = true
                )

            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.countries),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )

                Button(
                    onClick = { navController.navigate(Route.ChooseCountries.route) },
                    modifier = Modifier
                        .width(140.dp)
                        .height(60.dp),
                    colors = ButtonColors(
                        containerColor = adjustButton,
                        contentColor = Color.White,
                        disabledContainerColor = adjustButton,
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(stringResource(id = R.string.adjust))
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.genres),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )

                Button(
                    onClick = { navController.navigate(Route.ChooseCountries.route) },
                    modifier = Modifier
                        .width(140.dp)
                        .height(60.dp),
                    colors = ButtonColors(
                        containerColor = adjustButton,
                        contentColor = Color.White,
                        disabledContainerColor = adjustButton,
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(stringResource(id = R.string.adjust))
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )

            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    // ALSO: APPLY FILTERS AND REFRESH PAGE
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .width(235.dp)
                        .height(85.dp),
                ) {
                    Text(stringResource(id = R.string.apply))
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                )
            }
        }
    }
}