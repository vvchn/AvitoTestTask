package com.vvchn.avitotesttask.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.paging.LOGGER
import com.vvchn.avitotesttask.presentation.moviedetail_screen.MovieDetailScreenViewModel
import com.vvchn.avitotesttask.presentation.ui.theme.AvitoTestTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MovieDetailScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(viewModel)
        setContent {
            AvitoTestTaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android", viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, viewModel: ViewModel) {
    val movieDetailScreenViewModel = viewModel as MovieDetailScreenViewModel
    val myState = movieDetailScreenViewModel.state
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(10.dp))

        when (myState.value.isLoading) {
            true -> Text(text = "Загрузка...")
            false -> if (myState.value.error == "") {
                Text(text = "Название фильма: ${myState.value.movie?.name}")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Ошибка (если будет): ${myState.value.error}")
                Log.d("ErrorValue", myState.value.error)
            } else {
                Text(text = myState.value.error)
            }
        }
    }

}

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AvitoTestTaskTheme {
        Greeting("Android",)
    }
}*/
