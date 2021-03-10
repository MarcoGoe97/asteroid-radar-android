package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application) : ViewModel () {

    private val database = AsteroidDatabase.getInstance(application)
    private val asteroidRepository = AsteroidsRepository(database)

    val asteroids = asteroidRepository.asteroids
    val pictureOfDay = asteroidRepository.pictureOfDay

    init {
        viewModelScope.launch {
            try {
                asteroidRepository.refreshData()
            } catch (e: Exception) {
                print(e.message)
            }
        }
    }

    fun updateFilters(asteroidsFilter: AsteroidsRepository.AsteroidsFilter) {
        asteroidRepository.updateAsteroidsFilter(asteroidsFilter)
    }
}