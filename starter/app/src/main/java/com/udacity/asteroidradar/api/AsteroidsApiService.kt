package com.udacity.asteroidradar.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AsteroidsApiService {
    @GET("feed")
    suspend fun getAsteroids(@Query("start_date") startDate: String, /*@Query("end_date") endDate: String,*/ @Query("api_key") apiKey: String) : String
}

object AsteroidsAPI {
    val retrofitService: AsteroidsApiService by lazy {
        retrofit.create(AsteroidsApiService::class.java)
    }
}