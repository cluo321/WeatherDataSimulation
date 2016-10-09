package com.cheng.weathersimulation.models

/**
  * Created by cheng on 7/10/2016.
  */

object WeatherConditions extends Enumeration {

  type WeatherCondition = Value

  // only three weather conditions considered
  val SNOW, RAIN, SUNNY = Value

}
