package com.cheng.weathersimulation.services

import breeze.stats.distributions.Gaussian

/**
  * Created by cheng on 7/10/2016.
  */

object RelativeHumidityGeneratorImpl extends RelativeHumidityGenerator {

  /**
    * Compute relative humidity as E/Es, where E is vapor pressure and Es is saturation vapor pressure.
    * The computation is based on the Clausius-Clapeyron equation.
    *
    * @param temperature
    * @return relative humidity in double (0 <= RH <= 1)
    */
  def generate(temperature: Double, latitude: Latitude, longitude: Longitude) : RelativeHumidity = {
    val dewPoint = generateDewPointTemperature(temperature, latitude, longitude)
    (dewPoint - temperature) match {
      case d if d <= 0 => 1.0 // saturation
      case _ => math.min(computeRelativeHumidity(temperature, dewPoint), 1.0)
    }
  }

  // use the Clausius-Clapeyron equation
  private def computeRelativeHumidity(temperature: Double, dewPoint: Double) : RelativeHumidity = {
    //val factor = 6.11
    val tKelvin = temperature + KelvinOffset
    val dewKelvin = dewPoint + KelvinOffset
    // the equation
    def f = (t : Double) => scala.math.exp((L/R_v) * (1/KelvinOffset - 1/t))
    val Es = f(tKelvin)
    val E = f(dewKelvin)

    Es/E
  }

  // Latent heat of vaporization
  private val L = 2453000L
  // Gas constant for moist air
  private val R_v = 461

  /**
    * Use modified normal distribution to randomly draw the difference between Dew Point and input temperature.
    * Set the fluctuation as 5 celsius..
    * Assume that the larger the latitude is, the larger the standard deviation is. In other words, for areas with large
    * latitude, it is assumed that the dew point is large
 *
    * @param temperature
    * @param latitude
    * @param longitude
    * @return
    */
  private def generateDewPointTemperature(temperature: Double, latitude: Latitude, longitude: Longitude) : Double = {
    val distance = scala.math.abs(latitude)/90 //also normalization
    val sd = 1 + scala.math.exp(distance) // standard deviation

    val normalDist = Gaussian(0, sd)
    val diff = normalDist.draw

    temperature + FLUCTUATION*diff
  }

  private val FLUCTUATION = 5


}
