package com.vvchn.avitotesttask.presentation.moviedetail_screen

import androidx.lifecycle.ViewModel
import com.vvchn.avitotesttask.data.repository.KinopoiskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailScreenViewModel @Inject constructor(
    private val api: KinopoiskRepository
) : ViewModel() {

}