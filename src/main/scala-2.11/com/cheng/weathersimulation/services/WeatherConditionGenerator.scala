package com.cheng.weathersimulation.services

import com.cheng.weathersimulation.models.WeatherConditions.WeatherCondition

/**
  * Created by cheng on 7/10/2016.
  */

trait WeatherConditionGenerator {
  def generate(temperature: Double, rh: RelativeHumidity, latitude: Latitude, longitude: Longitude) : WeatherCondition
}
