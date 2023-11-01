package com.example.retrofit.data.weather

class RepositoryWeather(val api: WeatherApi) {

    suspend fun getDataWeather(key: String, city: String, number: String, aqua: String, nothing: String) = api.getDataWeather(key, city, number, aqua, nothing)

}