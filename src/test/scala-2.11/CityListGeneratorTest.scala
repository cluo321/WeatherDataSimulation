import com.cheng.weathersimulation.services.CityListGeneratorImpl
import org.specs2.mutable._

/**
  * Created by cheng on 7/10/2016.
  */
object CityListGeneratorTest extends Specification {

  "City List Generator" should {

    "when requiring 1 city, generate 1 city" in {
      val num = 1
      val cities = CityListGeneratorImpl.generateCityList(num)

      cities.size must beEqualTo(num)
    }

    "when requiring 10 cities, generate 1 city" in {
      val num = 10
      val cities = CityListGeneratorImpl.generateCityList(num)

      cities.size must beEqualTo(num)
    }
  }

}
