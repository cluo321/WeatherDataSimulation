package com.cheng.weathersimulation.services

import org.joda.time.DateTime

/**
  * Created by cheng on 6/10/2016.
  */

trait DateTimeGenerator {
  def generate(start: DateTime, end: DateTime) : DateTime
}
