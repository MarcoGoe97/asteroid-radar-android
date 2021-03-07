package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {
    @Query("select * from databaseasteroid where closeApproachDate >= :start_date order by closeApproachDate asc")
    fun getAsteroids(start_date: String): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)

    @Query("delete from databaseasteroid where closeApproachDate < :current_date")
    fun deleteOldAsteroids(current_date: String)
}