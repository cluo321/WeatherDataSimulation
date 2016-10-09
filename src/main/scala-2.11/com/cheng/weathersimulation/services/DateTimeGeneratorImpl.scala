package com.cheng.weathersimulation.services

import org.joda.time.DateTime

import scala.util.Random

/**
  * Created by cheng on 6/10/2016.
  */

object DateTimeGeneratorImpl extends DateTimeGenerator {

  def generate(start: DateTime, end: DateTime) : DateTime = {
    // the time only needs to be as accurate as second
    val startVal = start.getMillis / 1000L
    val endVal = end.getMillis / 1000L
    if(startVal == endVal){ // if equals, return start date
      start
    }else{
      val range : Long = endVal - startVal
      val offset = math.abs(randomizer.nextLong()) % range // nextLong may generate negative values
      val sampled : Long = start.getMillis + offset*1000L
      new DateTime(sampled)
    }
  }

  private lazy val randomizer = new Random()


}
