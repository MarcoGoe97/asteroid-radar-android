package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.repository.api.Asteroid
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.repository.api.PictureOfDay
import com.udacity.asteroidradar.repository.api.AsteroidsAPI
import com.udacity.asteroidradar.repository.api.asDatabaseModel
import com.udacity.asteroidradar.repository.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repository.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.database.asDomainModel
import com.udacity.asteroidradar.extentions.toSimpleString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class AsteroidsRepository(private val database: AsteroidDatabase) {

    enum class AsteroidsFilter {
        WEEK, TODAY, ALL
    }

    private val _asteroidsFilter = MutableLiveData(AsteroidsFilter.ALL)

    val asteroids: LiveData<List<Asteroid>> = Transformations.switchMap(_asteroidsFilter) { filter ->
        if(filter == AsteroidsFilter.ALL) {
            Transformations.map(database.asteroidDoa.getAsteroidsFrom(Date().toSimpleString())) {
                it.asDomainModel()
            }
        } else {
            var endDate = Date().toSimpleString()
            if(filter == AsteroidsFilter.WEEK) {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)

                val year = calendar[Calendar.YEAR]
                val month = calendar[Calendar.MONTH] + 1
                val formattedMonth = month.toString().padStart(2, '0')
                val day = calendar[Calendar.DAY_OF_MONTH]
                val formattedDay = day.toString().padStart(2, '0')
                endDate = "$year-$formattedMonth-$formattedDay"
            }

            Transformations.map(database.asteroidDoa.getAsteroidsBetween(Date().toSimpleString(), endDate)) {
                it.asDomainModel()
            }
        }
    }

    val pictureOfDay: LiveData<PictureOfDay> = Transformations.map(database.asteroidDoa.getPictureOfDay()) {
        it?.asDomainModel()
    }

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    suspend fun refreshData() {
        try {
            _loading.value = true
            refreshAsteroids()
            refreshPictureOfDay()
            _loading.value = false
        } catch (e: Exception) {
            _loading.value = false
        }
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroidsString =
                AsteroidsAPI.retrofitScalarsService.getAsteroids(Date().toSimpleString(), BuildConfig.API_KEY)
            val asteroids = parseAsteroidsJsonResult(JSONObject(asteroidsString))
            database.asteroidDoa.insertAll(*asteroids.asDatabaseModel())
        }
    }

    private suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            val pictureOfDay = AsteroidsAPI.retrofitMoshiService.getPictureOfDay(BuildConfig.API_KEY)
            if(pictureOfDay.mediaType == "image") {
                database.asteroidDoa.insert(pictureOfDay.asDatabaseModel())
            }
        }
    }

    suspend fun deleteOldAsteroids() {
        withContext(Dispatchers.IO){
            database.asteroidDoa.deleteOldAsteroids(Date().toSimpleString())
        }
    }

    fun updateAsteroidsFilter(filter: AsteroidsFilter) {
        _asteroidsFilter.value = filter
    }
}