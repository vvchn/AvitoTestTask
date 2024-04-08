package com.vvchn.avitotesttask.presentation.main_screen

import androidx.lifecycle.ViewModel
import com.vvchn.avitotesttask.data.repository.KinopoiskRepository
import com.vvchn.avitotesttask.data.repository.KinopoiskRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val api: KinopoiskRepository
) : ViewModel() {

}