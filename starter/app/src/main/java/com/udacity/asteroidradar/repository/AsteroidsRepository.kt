package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.api.AsteroidsAPI
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject

class AsteroidsRepository {

    suspend fun refreshAsteroids() {
        val asteroidsString = AsteroidsAPI.retrofitService.getAsteroids("2021-03-06", BuildConfig.API_KEY)
        val asteroids = parseAsteroidsJsonResult(JSONObject(asteroidsString))
        //TODO Safe in DB
    }
}