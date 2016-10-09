package com.cheng.weathersimulation.services

import breeze.linalg.DenseVector
import breeze.stats.distributions.Multinomial
import com.cheng.weathersimulation.models.WeatherConditions
import com.cheng.weathersimulation.models.WeatherConditions._

/**
  * Created by cheng on 7/10/2016.
  */

object WeatherConditionGeneratorImpl extends WeatherConditionGenerator {

  /**
    * For simplicity, only model the weather condition as the result of temperature, relative humidity and latitude.
    * Different multi-nominal distributions of weather conditions are selected based on the range in which the input
    * temperature falls.
    *
    * @param temperature unit in Celsius.
    * @param rh Relative Humidity.
    * @param latitude can be found from CityInfo.
    * @param longitude can be found from CityInfo.
    * @return a wempiricallytion from an empiricially linear model with day of month as the input variable.
    */
  def generate(temperature: Double, rh: RelativeHumidity, latitude: Latitude, longitude: Longitude) : WeatherCondition = {

    (temperature, rh) match {
      case (t, r) if t <= 0 && rh >=1 => SNOW // cold and saturation
      case (t, r) if t > 0 && rh >=1 => RAIN  // normal but saturation
      case (_, r) if rh < 0.4 => SUNNY // low humidity
      case _ =>
        val feeling = getFeelingFromTemperature(temperature)
        val eventProbs = generateEventProbs(rh, latitude, longitude)(feeling)

        // always treat Snow as 0, Rain as 1, Sunny as 2
        val values = Array(eventProbs(SNOW), eventProbs(RAIN), eventProbs(SUNNY))

        val sampler = Multinomial(DenseVector(values))
        val index = sampler.draw()
        WeatherConditions(index)
    }
  }

  // Because only three weather conditions are considered.
  // Empirically specify the values of event probabilities.
  // Meanwhile, adjust the specify value according to the distant to equator. Assume that the closer to the equator,
  // the more possible to have a rainy weather.
  // Furthermore, the larger relative humidity is, the more possible it is rainy or snowy.
  private def generateEventProbs(rh: RelativeHumidity, latitude: Latitude, longitude: Longitude)
  : Map[Feelings, Map[WeatherCondition, Double]] = {
    val locEffect = locationEffect(latitude, longitude)
    val rhEffectInNormal = humidityEffect(rh, 1 - baseFactor - sunnyBaseInNormal)
    val rhEffectInHot = humidityEffect(rh, 1 - baseFactor - sunnyBaseInHot)

    val rainInNormal = 1 - sunnyBaseInNormal - baseFactor + locEffect + rhEffectInNormal
    val rainInHot = 1 - sunnyBaseInHot - baseFactor + locEffect + rhEffectInHot
    val sunnyInFreeze = 1 - snowInFreeze - baseFactor
    val sunnyInNormal = sunnyBaseInNormal - locEffect - rhEffectInNormal
    val sunnyInHot = sunnyBaseInHot - locEffect - rhEffectInHot

    Map(
      Freeze -> Map( SNOW -> snowInFreeze, RAIN -> baseFactor, SUNNY -> sunnyInFreeze ),
      Normal -> Map( SNOW -> baseFactor, RAIN -> rainInNormal, SUNNY -> sunnyInNormal ),
      Hot    -> Map( SNOW -> baseFactor, RAIN -> rainInHot, SUNNY -> sunnyInHot )
    )
  }

  // Empirically set
  private val sunnyBaseInNormal = 0.60
  private val sunnyBaseInHot = 0.80
  private val snowInFreeze = 0.35
  // allow a valid multi-nomial distribution
  private val baseFactor = 0.00001

  // Currently, only use latitude between the current location and the equator which is always 0.
  private def locationEffect(latitude: Latitude, longitude: Longitude) : Double = {
    // one degree approximately equals to 111km
    val factor = 111

    // model the max factor as 5/111
    5/(factor + math.abs(latitude))
  }

  // Assume that when rh >= 0.5, it increases the probability of rain.
  // When rh < 0.5, it decreases the probability of rain.
  private def humidityEffect(rh: RelativeHumidity, rainProb: Double) : Double = {
    if(rh >= 0.5) {
      0.1 * math.exp(rh - 1)
    }else{
      val offset = 0.5 // very unlikely to rain when rh < 0.5
      -1 * rainProb * math.exp(-1 * math.max(0, rh - offset))
    }
  }

  /**
    * Empirically divide the range of temperatures into personal feelings
    *
    * @param temperature unit in Celsius.
    * @return personal feeling to select event probabilities for generating weather conditions
    */
  private def getFeelingFromTemperature(temperature: Double) : Feelings = {
    temperature match {
      case t if t < 0 => Freeze
      case t if t > 30.0 => Hot
      case _ => Normal
    }
  }

  private sealed trait Feelings
  private case object Freeze extends Feelings
  private case object Normal extends Feelings
  private case object Hot extends Feelings

}
