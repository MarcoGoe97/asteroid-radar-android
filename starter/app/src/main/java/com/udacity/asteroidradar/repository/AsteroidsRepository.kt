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
import com.udacity.asteroidradar.extentions.toSimpleString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*

class AsteroidsRepository (private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDoa.getAsteroids(Date().toSimpleString())) {
        it.asDomainModel()
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroidsString =
                AsteroidsAPI.retrofitService.getAsteroids(Date().toSimpleString(), BuildConfig.API_KEY)
            val asteroids = parseAsteroidsJsonResult(JSONObject(asteroidsString))
            database.asteroidDoa.insertAll(*asteroids.asDatabaseModel())
        }
    }

    suspend fun deleteOldAsteroids() {
        withContext(Dispatchers.IO){
            database.asteroidDoa.deleteOldAsteroids(Date().toSimpleString())
        }
    }
}