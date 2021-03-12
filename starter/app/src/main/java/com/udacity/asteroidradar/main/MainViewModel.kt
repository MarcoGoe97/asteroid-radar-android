package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel () {

    private val database = AsteroidDatabase.getInstance(application)
    private val asteroidRepository = AsteroidsRepository(database)

    val asteroids = asteroidRepository.asteroids
    val pictureOfDay = asteroidRepository.pictureOfDay
    val loading = asteroidRepository.loading

    init {
        viewModelScope.launch {
            asteroidRepository.refreshData()
        }
    }

    fun updateFilters(asteroidsFilter: AsteroidsRepository.AsteroidsFilter) {
        asteroidRepository.updateAsteroidsFilter(asteroidsFilter)
    }
}