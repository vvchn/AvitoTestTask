package com.vvchn.avitotesttask.presentation.mainscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import com.vvchn.avitotesttask.R
import com.vvchn.avitotesttask.presentation.Dimens
import com.vvchn.avitotesttask.presentation.navgraph.Route
import com.vvchn.avitotesttask.presentation.ui.theme.adjustButton
import com.vvchn.avitotesttask.presentation.ui.theme.searchBarColor
import com.vvchn.avitotesttask.presentation.ui.theme.topBarColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseCountries(vm: MainScreenViewModel, navController: NavController) {
    val uiState by vm.state.collectAsStateWithLifecycle()

    Surface(
        color = searchBarColor, modifier = Modifier
            .fillMaxSize()
            .background(searchBarColor)
    ) {
        Scaffold(
            containerColor = searchBarColor,
            topBar = {
                TopAppBar(title = {
                    Text(
                        text = stringResource(id = R.string.chooseCountries),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "backToFiltersList",
                            tint = Color.White
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = topBarColor))
            },
        ) { innerPadding ->
            if (uiState.possibleCountries.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (uiState.isCountriesLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.no_data),
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.an_error_probably),
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                        )

                        Button(
                            onClick = {
                                vm.tryToLoadCountriesList()
                            },
                            modifier = Modifier
                                .width(140.dp)
                                .height(60.dp),
                            colors = ButtonColors(
                                containerColor = adjustButton,
                                contentColor = Color.White,
                                disabledContainerColor = adjustButton,
                                disabledContentColor = Color.White
                            ),
                            shape = RectangleShape,
                        ) {
                            Text(stringResource(id = R.string.try_again))
                        }
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.padding(innerPadding)) {
                    items(uiState.possibleCountries, key = { it.country.name ?: "" }) { country ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = country.country.name ?: "-",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 5.dp, end = 10.dp),
                                color = Color.White
                            )
                            Checkbox(
                                modifier = Modifier
                                    .wrapContentWidth(),
                                checked = country.isSelected,
                                onCheckedChange = { choice ->
                                    vm.onCountryCheckboxClicked(country.country.name ?: "", choice)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseGenres(vm: MainScreenViewModel, navController: NavController) {
    val uiState by vm.state.collectAsStateWithLifecycle()

    Surface(
        color = searchBarColor, modifier = Modifier
            .fillMaxSize()
            .background(searchBarColor)
    ) {
        Scaffold(
            containerColor = searchBarColor,
            topBar = {
                TopAppBar(title = {
                    Text(
                        text = stringResource(id = R.string.chooseGenres),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "backToFiltersList",
                            tint = Color.White
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = topBarColor))
            },
        ) { innerPadding ->
            if (uiState.possibleGenres.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (uiState.isGenresLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.no_data),
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.an_error_probably),
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(15.dp)
                        )

                        Button(
                            onClick = {
                                vm.tryToLoadGenresList()
                            },
                            modifier = Modifier
                                .width(140.dp)
                                .height(60.dp),
                            colors = ButtonColors(
                                containerColor = adjustButton,
                                contentColor = Color.White,
                                disabledContainerColor = adjustButton,
                                disabledContentColor = Color.White
                            ),
                            shape = RectangleShape,
                        ) {
                            Text(stringResource(id = R.string.try_again))
                        }
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.padding(innerPadding)) {
                    items(uiState.possibleGenres, key = { it.genre.name ?: "" }) { genre ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = genre.genre.name ?: "-",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 5.dp, end = 10.dp),
                                color = Color.White
                            )
                            Checkbox(
                                modifier = Modifier
                                    .wrapContentWidth(),
                                checked = genre.isSelected,
                                onCheckedChange = { choice ->
                                    vm.onGenreCheckboxClicked(genre.genre.name ?: "", choice)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersBar(vm: MainScreenViewModel, navController: NavController) {
    val context = LocalContext.current
    var isYearSuccessfullyValidated: Boolean = true
    var isAgeRatingSuccessfullyValidated: Boolean = true
    val uiState by vm.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

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
                        contentDescription = "backToFiltersList",
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
                        Text(text = "1874")
                    },
                    colors = TextFieldDefaults.colors().copy(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
                    onValueChange = vm::collectLeftBoundDate,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
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
                        Text(text = "2050")
                    },
                    colors = TextFieldDefaults.colors().copy(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                    ),
                    onValueChange = vm::collectRightBoundDate,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
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
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
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
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
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
                    onClick = {
                        navController.navigate(Route.ChooseCountries.route)
                    },
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
                    onClick = {
                        navController.navigate(Route.ChooseGenres.route)
                    },
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
                    onClick = {
                        isYearSuccessfullyValidated = vm.validateUserInputYear()
                        isAgeRatingSuccessfullyValidated = vm.validateUserInputAgeRating()

                        if (isYearSuccessfullyValidated) {
                            if (isAgeRatingSuccessfullyValidated) {
                                vm.applyFilters()
                                vm.getMovies()
                                navController.popBackStack()
                            }
                        }
                    },
                    modifier = Modifier
                        .width(235.dp)
                        .height(85.dp),
                    shape = RectangleShape,
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

    LaunchedEffect(key1 = uiState.yearValidationErrorCode, key2 = uiState.ageValidationErrorCode) {
        if (uiState.yearValidationErrorCode != 0) {
            Toast.makeText(
                context,
                context.getString(uiState.yearValidationErrorCode),
                Toast.LENGTH_LONG
            ).show()
        }
        if (uiState.ageValidationErrorCode != 0) {
            Toast.makeText(
                context,
                context.getString(uiState.ageValidationErrorCode),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}