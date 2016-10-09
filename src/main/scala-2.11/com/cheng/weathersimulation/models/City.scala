package com.cheng.weathersimulation.models

/**
  * Created by cheng on 6/10/2016.
  */

object City extends Enumeration {
  type City = Value
  val CANBERRA, SYDNEY, MELBOURNE, BRISBANE, PERTH, HOBART, DARWIN = Value

  val numCities = 7
}
