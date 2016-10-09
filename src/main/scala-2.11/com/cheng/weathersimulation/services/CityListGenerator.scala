package com.cheng.weathersimulation.services

import com.cheng.weathersimulation.models.City
import com.cheng.weathersimulation.models.City.City

/**
  * Created by cheng on 6/10/2016.
  */

trait CityListGenerator {
  def generateCityList(num: Int) : Seq[City]

  protected val numCities = City.numCities
}
