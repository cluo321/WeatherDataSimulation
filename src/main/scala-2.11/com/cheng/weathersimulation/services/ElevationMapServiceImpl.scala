package com.cheng.weathersimulation.services

import javax.imageio.ImageIO

import scala.util.Try

/**
  * Created by cheng on 6/10/2016.
  */

class ElevationMapServiceImpl(file: String) extends ElevationMapService {

  def lookupElevation(latitude: Latitude, longitude: Longitude) : Elevation = {
    val el = bitmap map { b =>
      val (x, y) = coordinationTransform(latitude, longitude)
      // as mentioned in the instruction, elevation information is stored in the red channel
      (b.getRGB(x, y) >> 16) & 0x0000ff
    } recover { // always return 0 if something goes wrong
      case _ => 0
    }

    el.get
  }

  // get from resources folder so make sure file begins with '/'
  private val filePath = if(Option(file.charAt(0)) exists  {_ == '/' }) file else '/' + file
  private val inputURL = getClass.getResource(filePath)
  private lazy val bitmap = Try(ImageIO.read(inputURL))

  private val mapWidth = if(bitmap.isSuccess) bitmap.get.getWidth else 0
  private val mapHeight = if(bitmap.isSuccess) bitmap.get.getHeight else 0

  // formulas adopted are based on projected Easting and Northing coordinates from spherical latitude and longitude
  // these formulas have been simplified and not very accurate
  // details of formulas are shown in https://en.wikipedia.org/wiki/Mercator_projection#Derivation_of_the_Mercator_projection
  private def coordinationTransform(latitude : Latitude, longitude: Longitude) : IntCoordinate = {
    // get x value
    val x = (longitude+180)*(mapWidth/360)

    // convert from degrees to radians
    import scala.math._
    val latRad = latitude*Pi/180

    // get y value
    val mercatorN = log(tan((Pi/4)+(latRad/2)))
    val y     = mapHeight/2 - mapWidth * mercatorN/(2*Pi)

    // looks like the formula is not mapped to the provided map
    // do some calibration
    val xInt = x.toInt - 2
    val yInt = y.toInt - 5
    val boundedX = min(max(0, xInt), mapWidth)
    val boundedY = min(max(0, yInt), mapHeight)

    (boundedX, boundedY)
  }

}

object ElevationMapServiceImpl {
  def apply(file: String): ElevationMapServiceImpl = {
      val elevMap = new ElevationMapServiceImpl(file)
      if(elevMap.bitmap.isSuccess){
        elevMap
      }else{
        throw elevMap.bitmap.failed.get
      }
  }
}