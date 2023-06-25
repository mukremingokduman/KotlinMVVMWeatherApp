package com.mukremingokduman.weatherappclone.service

import com.mukremingokduman.weatherappclone.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    //https://api.openweathermap.org/data/2.5/weather?q=sakarya&appid=a3100d18e66fa7623ba5ed6eb1d2ed97

   // @GET("data/2.5/weather?q=sakarya&appid=a3100d18e66fa7623ba5ed6eb1d2ed97")
    @GET("data/2.5/weather?&units=metric&appid=a3100d18e66fa7623ba5ed6eb1d2ed97")
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>


}