package com.cheng.weathersimulation.simulators

import com.cheng.weathersimulation.datasources.CityInfo
import com.cheng.weathersimulation.models.WeatherData
import com.cheng.weathersimulation.services._
import org.joda.time.DateTime

import scala.concurrent.Future

/**
  * Created by cheng on 7/10/2016.
  */

class WeatherSimulator(cityListGenerator: CityListGenerator,
                       dateTimeGenerator: DateTimeGenerator,
                       temperatureGenerator: TemperatureGenerator,
                       pressureGenerator: PressureGenerator,
                       relativeHumidityGenerator: RelativeHumidityGenerator,
                       weatherConditionGenerator: WeatherConditionGenerator,
                       elevationMapService: ElevationMapServiceImpl) {

  def simulate(nums: Int, start: DateTime, end: DateTime): Future[Seq[WeatherData]] = {
    val cities = cityListGenerator.generateCityList(nums)

    import scala.concurrent.ExecutionContext.Implicits.global
    Future(0 until cities.size) map { (indices : Seq[Int] ) => indices map {i =>
        val city = cities(i)
        val (latitude, longitude) = CityInfo.GeoLocations(city)
        val elevation = elevationMapService.lookupElevation(latitude, longitude)
        val dateTime = dateTimeGenerator.generate(start, end)
        val temperature = temperatureGenerator.generate(city, dateTime)
        val pressure = pressureGenerator.generate(temperature, elevation)
        val rh = relativeHumidityGenerator.generate(temperature, latitude, longitude)
        val condition = weatherConditionGenerator.generate(temperature, rh, latitude, longitude)

        WeatherData(city, latitude, longitude, elevation, dateTime, condition, temperature, pressure.toFloat, rh.toFloat)
      }
    }
  }

}

object WeatherSimulator {
  /**
    *
    * @param file the filename of bitmap, which should be placed in resources folder
    * @return
    */
  def apply(file: String) : WeatherSimulator = {
    val elevationMapService = ElevationMapServiceImpl(file)

    new WeatherSimulator(CityListGeneratorImpl,
                          DateTimeGeneratorImpl,
                          TemperatureGeneratorImpl,
                          PressureGeneratorImpl,
                          RelativeHumidityGeneratorImpl,
                          WeatherConditionGeneratorImpl,
                          elevationMapService)
  }
}
