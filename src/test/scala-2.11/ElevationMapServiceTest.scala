import com.cheng.weathersimulation.datasources.CityInfo
import com.cheng.weathersimulation.services.ElevationMapServiceImpl
import org.specs2.mutable._

/**
  * Created by cheng on 7/10/2016.
  */
object ElevationMapServiceTest extends Specification {

  private val file = "elevation.bmp"
  private val elevationMapService = ElevationMapServiceImpl(file)

  "Elevation Map Service" should {

    import com.cheng.weathersimulation.models.City._

    "lookup the elevation of SYDNEY, which should be greater than 0" in {
      val (latitude, altitude) = CityInfo.GeoLocations(SYDNEY)
      val elevation = elevationMapService.lookupElevation(latitude, altitude)

      elevation must beGreaterThan(0)
    }

  }

}
