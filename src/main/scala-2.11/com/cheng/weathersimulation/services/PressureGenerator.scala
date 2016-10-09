package com.cheng.weathersimulation.services

/**
  * Created by cheng on 7/10/2016.
  */

trait PressureGenerator {
  def generate(temperature: Double, elevation: Elevation) : Pressure
}
