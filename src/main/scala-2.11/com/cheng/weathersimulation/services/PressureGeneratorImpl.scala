package com.cheng.weathersimulation.services

/**
  * Created by cheng on 7/10/2016.
  */

object PressureGeneratorImpl extends PressureGenerator {

  /**
    * Model the pressure as the function of temperature and elevation.
    * The formula adopted is the Barometric formula, which can be found at https://en.wikipedia.org/wiki/Barometric_formula.
    * Assume that the standard temperature lapse rate is non zero in the formula.
    * Assume that the range of (above sea level) is always below 11km.
    *
    * @param temperature unit in Celsius
    * @param elevation can be found by using ElevationMapService
    * @return
    */
  def generate(temperature: Double, elevation: Elevation) : Pressure = {
    // convert from celsius to Kelvin
    val kTemp = temperature + KelvinOffset

    // Barometric formula
    val value1 = kTemp / (kTemp + L_b * (elevation - h_b))
    val value2 = (g_0 * M) / (R_s * L_b)
    P_b * math.pow(value1, value2)
  }

  // parameters in formula
  // static pressure
  private val P_b = 101325.0
  // standard temperature in K
  private val T_b = 288.15
  // standard temperature lapse rate
  private val L_b = -0.0065
  // height at bottom of layer b
  private val h_b = 0
  // universal gas constant
  private val R_s = 8.31432
  //  gravitational acceleration
  private val g_0 = 9.80665
  // molar mass of Earth's air
  private val M = 0.0289644

}
