package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.api.AsteroidsAPI
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository (private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDoa.getAsteroids()) {
        it.asDomainModel()
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroidsString =
                AsteroidsAPI.retrofitService.getAsteroids("2021-03-06", BuildConfig.API_KEY)
            val asteroids = parseAsteroidsJsonResult(JSONObject(asteroidsString))
            database.asteroidDoa.insertAll(*asteroids.asDatabaseModel())
        }
    }
}