package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {
    @Query("select * from databaseasteroid where closeApproachDate >= :start_date and closeApproachDate <= :end_date order by closeApproachDate asc")
    fun getAsteroidsBetween(start_date: String, end_date: String): LiveData<List<DatabaseAsteroid>>

    @Query("select * from databaseasteroid where closeApproachDate >= :start_date order by closeApproachDate asc")
    fun getAsteroidsFrom(start_date: String): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)

    @Query("delete from databaseasteroid where closeApproachDate < :current_date")
    fun deleteOldAsteroids(current_date: String)

    @Query("select * from databasepictureofday limit 1")
    fun getPictureOfDay(): LiveData<DatabasePictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pictureOfDay: DatabasePictureOfDay)
}