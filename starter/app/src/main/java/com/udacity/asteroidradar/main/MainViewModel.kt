package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids : LiveData<List<Asteroid>>
        get() = _asteroids

    private val asteroidRepository = AsteroidsRepository()

    init {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }

        _asteroids.value =  listOf(
            Asteroid(3726710, "433 Eros (A898 PA)", "1900-12-27", 24.3,
                0.0820427065, 19.4850295284, 0.0269230459, true), Asteroid(1, "Test2", "17.05.2021", 1.0,
                5.0, 70.0, 50.0, false), Asteroid(2, "Test3", "17.05.2021", 1.0,
                5.0, 70.0, 50.0, true), Asteroid(3726710, "433 Eros (A898 PA)", "1900-12-27", 24.3,
            0.0820427065, 19.4850295284, 0.0269230459, true),  Asteroid(3726710, "433 Eros (A898 PA)", "1900-12-27", 24.3,
                0.0820427065, 19.4850295284, 0.0269230459, true),  Asteroid(3726710, "433 Eros (A898 PA)", "1900-12-27", 24.3,
                0.0820427065, 19.4850295284, 0.0269230459, true),  Asteroid(3726710, "433 Eros (A898 PA)", "1900-12-27", 24.3,
                0.0820427065, 19.4850295284, 0.0269230459, true),  Asteroid(3726710, "433 Eros (A898 PA)", "1900-12-27", 24.3,
                0.0820427065, 19.4850295284, 0.0269230459, true),  Asteroid(3726710, "433 Eros (A898 PA)", "1900-12-27", 24.3,
                0.0820427065, 19.4850295284, 0.0269230459, true),  Asteroid(3726710, "433 Eros (A898 PA)", "1900-12-27", 24.3,
                0.0820427065, 19.4850295284, 0.0269230459, true),  Asteroid(3726710, "433 Eros (A898 PA)", "1900-12-27", 24.3,
                0.0820427065, 19.4850295284, 0.0269230459, true)
        )
    }
}