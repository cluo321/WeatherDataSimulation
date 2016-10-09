package com.cheng.weathersimulation.services

import com.cheng.weathersimulation.models.City
import com.cheng.weathersimulation.models.City.City

import scala.util.Random

/**
  * Created by cheng on 6/10/2016.
  */

object CityListGeneratorImpl extends CityListGenerator {

  def generateCityList(num: Int) : Seq[City] = {
    val randomizer = new Random()
    val indices =
      for {
        i <- 0 until num
        index <- Seq(randomizer.nextInt(numCities))
      } yield index
    indices map {i => City.apply(i) }
  }
}
