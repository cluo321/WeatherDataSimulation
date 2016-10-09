package com.cheng.weathersimulation.models

/**
  * Created by cheng on 6/10/2016.
  */

object City extends Enumeration {
  type City = Value
  val CANBERRA, SYDNEY, BRISBANE, PERTH, DARWIN, ORANGE, WUHAN, CHENGDU, BEIJING,
      PARIS, BERLIN, WASHINGTON, NEW_YORK, NEW_DELI, SEOUL = Value

  val numCities = 15
}
