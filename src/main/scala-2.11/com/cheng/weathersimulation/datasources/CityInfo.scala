package com.cheng.weathersimulation.datasources

/**
  * Created by cheng on 6/10/2016.
  * Store the geolocation (latitude, longitude) of cities
  * we'd better obtain these information from a database table
  */

object CityInfo {
  import com.cheng.weathersimulation.models.City._

  // data are obtained from www.climatemps.com
  // unit: decimal degree
  val GeoLocations = Map(
    CANBERRA -> (-35.24, 149.07),
    SYDNEY -> (-33.86, 151.21),
    MELBOURNE -> (-37.8, 145.0),
    BRISBANE -> (-27.4, 153.1),
    PERTH -> (31.9, 115.8),
    HOBART -> (42.8, 147.3),
    DARWIN -> (12.5, 130.8)
  )

  // average of max and min temperatures per month
  // unit: Celsius
  // data are obtained from wikipedia
  val TemperatureRanges = Map(
    SYDNEY -> Seq((18.7, 25.9), (18.8, 25.8), (17.6, 24.8), (14.7, 22.4), (11.6, 19.5),
                  (9.3, 17.0), (8.1, 16.3), (9.0, 17.8), (11.1, 20.0), (13.6, 22.1), (15.7, 23.6), (17.5, 21.3)),
    CANBERRA -> Seq((18.7, 25.9), (18.8, 25.8), (17.6, 24.8), (14.7, 22.4), (11.6, 19.5),
      (9.3, 17.0), (8.1, 16.3), (9.0, 17.8), (11.1, 20.0), (13.6, 22.1), (15.7, 23.6), (17.5, 21.3)),
    MELBOURNE -> Seq((18.7, 25.9), (18.8, 25.8), (17.6, 24.8), (14.7, 22.4), (11.6, 19.5),
      (9.3, 17.0), (8.1, 16.3), (9.0, 17.8), (11.1, 20.0), (13.6, 22.1), (15.7, 23.6), (17.5, 21.3)),
    BRISBANE -> Seq((18.7, 25.9), (18.8, 25.8), (17.6, 24.8), (14.7, 22.4), (11.6, 19.5),
      (9.3, 17.0), (8.1, 16.3), (9.0, 17.8), (11.1, 20.0), (13.6, 22.1), (15.7, 23.6), (17.5, 21.3)),
    PERTH -> Seq((18.7, 25.9), (18.8, 25.8), (17.6, 24.8), (14.7, 22.4), (11.6, 19.5),
      (9.3, 17.0), (8.1, 16.3), (9.0, 17.8), (11.1, 20.0), (13.6, 22.1), (15.7, 23.6), (17.5, 21.3)),
    HOBART -> Seq((18.7, 25.9), (18.8, 25.8), (17.6, 24.8), (14.7, 22.4), (11.6, 19.5),
      (9.3, 17.0), (8.1, 16.3), (9.0, 17.8), (11.1, 20.0), (13.6, 22.1), (15.7, 23.6), (17.5, 21.3)),
    DARWIN -> Seq((18.7, 25.9), (18.8, 25.8), (17.6, 24.8), (14.7, 22.4), (11.6, 19.5),
      (9.3, 17.0), (8.1, 16.3), (9.0, 17.8), (11.1, 20.0), (13.6, 22.1), (15.7, 23.6), (17.5, 21.3))
  )
}