package com.cheng.weathersimulation.services

/**
  * Created by cheng on 7/10/2016.
  */

trait RelativeHumidityGenerator {
  def generate(temperature : Double, latitude: Latitude, longitude: Longitude) : RelativeHumidity
}
