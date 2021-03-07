package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getInstance
import com.udacity.asteroidradar.repository.AsteroidsRepository
import retrofit2.HttpException

class RefreshDataWorker (appContext: Context, params: WorkerParameters) :
        CoroutineWorker(appContext, params){

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val repository = AsteroidsRepository(database)
        return try {
            repository.deleteOldAsteroids()
            repository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException){
            Result.retry()
        }
    }
}