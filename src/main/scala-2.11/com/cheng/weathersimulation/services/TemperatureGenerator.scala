package com.cheng.weathersimulation.services

import com.cheng.weathersimulation.models.City.City
import org.joda.time.DateTime

/**
  * Created by cheng on 8/10/2016.
  */

trait TemperatureGenerator {
  def generate(city: City, dateTime: DateTime) : Temperature
}
