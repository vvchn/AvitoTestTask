package com.vvchn.avitotesttask.presentation.main_screen

import androidx.lifecycle.ViewModel
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val api: KinopoiskRepository
) : ViewModel() {

}