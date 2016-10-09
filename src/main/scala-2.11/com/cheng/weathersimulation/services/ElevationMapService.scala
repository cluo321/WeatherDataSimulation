package com.cheng.weathersimulation.services

/**
  * Created by cheng on 6/10/2016.
  */

trait ElevationMapService {
  def lookupElevation(latitude: Latitude, longitude: Longitude) : Elevation
}
