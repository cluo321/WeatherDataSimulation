package com.cheng.weathersimulation.models

import com.cheng.weathersimulation.models.City.City
import com.cheng.weathersimulation.models.WeatherConditions.WeatherCondition
import com.cheng.weathersimulation.services.{Latitude, Longitude}
import com.cheng.weathersimulation.utils.PrettyPrinter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

/**
  * Created by cheng on 7/10/2016.
  */

case class WeatherData (city : City,
                        latitude: Latitude,
                        longitude: Longitude,
                        elevation : Int,
                        dateTime : DateTime,
                        condition : WeatherCondition,
                        temperature : Float,
                        pressure : Float,
                        humidity : Float) {
  override def toString = {
    val cityName = PrettyPrinter.prettyCityName(city.toString)

    val latLongStr = Array(latitude, longitude) map {_.toString} mkString(",")
    val elevationStr = elevation.toString // avoid to print decimal .0
    val positionStr = Array(latLongStr, elevationStr) mkString(",")

    val conditionStr = condition.toString.toLowerCase.capitalize

    val tStr = formatValue(temperature)
    val temperatureStr = if(temperature >= 0) "+" + tStr else tStr

    val hPaPressure = pressure/100 // one HPa equals to 100 Pa
    val pressureStr = formatValue(hPaPressure)

    val humidityStr = (humidity*100).toInt.toString

    val dateTimeStr = dateTimePattern.print(dateTime)

    Array(cityName, positionStr, dateTimeStr, conditionStr, temperatureStr, pressureStr, humidityStr) mkString("|")
  }

  private def formatValue(value: Float) : String = {
    //val absValue = scala.math.abs(value)
    f"$value%.1f"
  }

  private val dateTimePattern = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
}
