package com.mukremingokduman.weatherappclone.service

import com.mukremingokduman.weatherappclone.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {


   // @GET("data/2.5/weather?q=sakarya&appid=your_api_token")
    @GET("data/2.5/weather?&units=metric&appid=your_api_token")
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>


}