package com.cheng.weathersimulation

/**
  * Created by cheng on 6/10/2016.
  */
package object services {

  // type aliases for easy understanding
  type Latitude = Double
  type Longitude = Double
  type Elevation = Int
  type RelativeHumidity = Double
  type Temperature = Float
  type Pressure = Double

  // integer coordinates
  type IntCoordinate = (Int, Int)

  val KelvinOffset = 273.15
}
