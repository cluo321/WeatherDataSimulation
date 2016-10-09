package com.cheng.weathersimulation.services

import com.cheng.weathersimulation.models.City
import com.cheng.weathersimulation.models.City.City

/**
  * Created by cheng on 6/10/2016.
  */

object CityListGeneratorImpl extends CityListGenerator {

  def generateCityList(num: Int) : Seq[City] = {
    val indices = util.Random.shuffle( 0 to (numCities-1) ).take(num)

    indices.toSeq map { i : Int => City.apply(i) }
  }
}
