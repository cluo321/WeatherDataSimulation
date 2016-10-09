package com.cheng.weathersimulation

import com.cheng.weathersimulation.simulators.WeatherSimulator
import com.cheng.weathersimulation.utils.{DateTimeParser, SimulatorResultRecorder}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try

/**
  * Created by cheng on 6/10/2016.
  */

object WeatherSimulation extends App {
  Try {
    if (3 != args.size) {
      println("Please enter the correct number of input args.")
      println("1st arg: the number of simulated weather data.")
      println("2nd arg: the start date of simulated weather data. YYYY/MM/DD ")
      println("3rd arg: the end date of simulated weather data. YYYY/MM/DD ")
      throw new IllegalArgumentException("Not enough arguments (exact 3 args needed).")
    }

    implicit val numOfSimulation = Integer.parseInt(args(0))

    // arg1 should be start date and arg2 should be end date
    val arg1 = args(1)
    val arg2 = args(2)

    val dates = DateTimeParser.checkAndParseDate(arg1, arg2)

    val fileName = "elevation.bmp"

    val simulator = WeatherSimulator(fileName)

    val startDate = dates(0)
    val endDate = dates(1)

    Await.ready(SimulatorResultRecorder.process(simulator.simulate(numOfSimulation, startDate, endDate)), Duration.Inf)
  } recover {
    case e : Throwable =>
      println("Something goes wrong with WeatherSimulation. Please check the following message.")
      println(e.getMessage)
  }
}
