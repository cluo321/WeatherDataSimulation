package com.cheng.weathersimulation.services

import breeze.stats.distributions.{Gaussian, StudentsT}
import com.cheng.weathersimulation.datasources.CityInfo
import com.cheng.weathersimulation.models.City.City
import org.joda.time.DateTime

/**
  * Created by cheng on 7/10/2016.
  * Due to the complexities of estimating weather accurately and lack of data, use an empirically linear model to model the generation of
  * temperature at various locations in different months.
  * Due to lack of training data (features and their corresponding temperature), I simply use a linear function to capture the base temperature
  * within every month. The endpoints are the average temperatures of current month and next month.
  * Instead of using distance between location (latitude, longitude) and the equator, use real data as the base temperature.
  * Use PDF of Student Distribution to model the changes of temperature between the estimated current max and min temperatures
  * from 0:00 to 24:00 within a specific month.
  * Elevation also have inverse effects on temperature. The higher it is, the lower the temperature value should be.
  * Also add a noise controlled by standard normal distribution to the output of PDF curve because the average max and min temperatures are used.
  * Loosely assume that max temperature is reached at 15:30 and min temperature is reached at 6:00.
  * The degree of freedom $\nu$ is chosen as 6 because CDF = 0.9995 when t = 6 and its PDF still has a reasonable length of long tails.
  */

object TemperatureGeneratorImpl extends TemperatureGenerator {

  def generate(city: City, dateTime: DateTime) : Temperature = {
    val month = dateTime.monthOfYear.get
    val day = dateTime.dayOfMonth.get
    val minute = dateTime.minuteOfDay.get

    val (currMin, currMax) = CityInfo.TemperatureRanges(city)(month-1)
    val (nextMin, nextMax) = CityInfo.TemperatureRanges(city)(month%12)

    val estimatedMin = estimateCurrentTemperature(currMin, nextMin)(day)
    val estimatedMax = estimateCurrentTemperature(currMax, nextMax)(day)

    val t = timeCoordinateTransform(minute)

    val latitude = CityInfo.GeoLocations(city)._1
    val longitude = CityInfo.GeoLocations(city)._2
    val elevation = elevationMap.lookupElevation(latitude, longitude)

    val temperatureIndex = studentPdf(t) + elevationEffect(elevation) + noiseDeviation*normalT.draw

    val scalaFactor = 1/maxPdf
    val result = scalaFactor * temperatureIndex * (estimatedMax-estimatedMin) + estimatedMin
    result.toFloat
  }

  /*
    * For simplicity, always assume there are 31 days in a month. This should not influence the results heavely under
    * the current linear model.
    * @param curr the average temperature of current month
    * @param next the average temperature of next month
    * @param dayOfMonth which day in the current month
    * @return the estimated temperature of dayOfMonth by using a linear funciton
  */
  private def estimateCurrentTemperature(curr: Double, next: Double)(dayOfMonth: Int) : Double = {
    val deltaY = next - curr
    val deltaX = 30
    val slope = deltaY/deltaX

    def f = (x : Int) => slope * x + curr

    f(dayOfMonth - 1)
  }

  private val bitmapFile = "elevation.bmp"
  private lazy val elevationMap = ElevationMapServiceImpl(bitmapFile)

  private val degreesOfFreedom = 6

  private val studentT = StudentsT(degreesOfFreedom)
  private val normalT = Gaussian(0, 1)
  private val noiseDeviation = 0.04 // because pdf = 0.4 when degrees of freedom is infinite
  private val maxPdf = 0.4
  private val elevationDeviation = 0.1 // assume that elevation has a large impact

  private def studentPdf(t: Float) : Double = {
    val numerator = scala.math.pow(1 + (t * t)/degreesOfFreedom, -(degreesOfFreedom  + 1)/2)
    val denominator = scala.math.exp(studentT.logNormalizer)
    numerator/denominator
  }

  /**
    * Assume that elevation have an inverse effect on temperature. The higher it is, the lower the temperature value should be.
    * Use exponential function to model this effect.
    *
    * @param elevation the elevation obtained from the elevation map
    * @return the computed influence of elevation on temperature
    */
  private def elevationEffect(elevation: Elevation) : Double = {
    // elevation could be zero...
    elevationDeviation*(scala.math.exp(-elevation))
  }

  /**
    * Convert from input minute of a day (range: 0 to 24*60) to [-6, 6] with respect to
    * max value when input minute is 15*60+30 and min value when input is 6*60
    *
    * @param minute
    * @return float values between -6 and 6. This returned value is used as input for CDF with degree of freedom 6.
    */
  private def timeCoordinateTransform(minute: Int) : Float = {
    if(minute >= 6*60){
      ((minute - 6*60)/(24*60))*12 + (-6)
    }else{
      ((minute + 18*60)/(24*60))*12 + (-6)
    }
  }

}
