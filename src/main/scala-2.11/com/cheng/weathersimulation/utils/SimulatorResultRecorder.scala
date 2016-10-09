package com.cheng.weathersimulation.utils

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import com.cheng.weathersimulation.models.WeatherData

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by cheng on 8/10/2016.
  */

object SimulatorResultRecorder {

  implicit val ec = ExecutionContext.fromExecutor(new java.util.concurrent.ForkJoinPool(1))

  def process(futureResults: Future[Seq[WeatherData]])(implicit numOfSimulation: Int) : Future[Unit] = {
    futureResults map { results =>
      results.size match {
        case s if s == numOfSimulation =>
          val outputStr = results mkString (System.lineSeparator)
          val filePath = new File(".").getCanonicalPath + File.separator + "output.txt"
          Files.write(Paths.get(filePath), outputStr.getBytes(StandardCharsets.UTF_8))
          // also output to console
          println(outputStr)

        case _ => println("Does not generate enough outputs.")
      }
    } recover {
      case e : Throwable => print(e.getMessage)
    }
  }

}
