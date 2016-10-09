import com.cheng.weathersimulation.services.RelativeHumidityGeneratorImpl
import org.specs2.mutable._

/**
  * Created by cheng on 7/10/2016.
  */
object RelativeHumidityGeneratorTest extends Specification {

  "Relative Humidity Generator" should {

    "generate value between 0.0 and 1.0" in {
      val temperature = 23.2
      val latitude = 12.3
      val longitude = 56.7

      val rh = RelativeHumidityGeneratorImpl.generate(temperature, latitude, longitude)

      rh must beBetween(0.0, 1.0)
    }

    "always have saturation cases" in {
      val temperature = 23.2
      val latitude = 12.3
      val longitude = 56.7

      val trails = 10
      val rhs = 0 until trails map { _ => RelativeHumidityGeneratorImpl.generate(temperature, latitude, longitude) }

      rhs must haveSize(trails)
      rhs must contain(be_==(1.0)).atLeastOnce  //contain { (x : Double) => x must beEqualTo(1.0) }
    }

    "always have unsaturation cases" in {
      val temperature = 23.2
      val latitude = 12.3
      val longitude = 56.7

      val trails = 10
      val rhs = 0 until trails map { _ => RelativeHumidityGeneratorImpl.generate(temperature, latitude, longitude) }

      rhs must haveSize(trails)
      rhs must contain(between(0.0, 0.99)).atLeastOnce //{ (x : Double) => x must beBetween(0.0, 0.99) }
    }

    "always have low RH values when the latitude is high and temperature is low" in {
      val temperature = 13.2
      val latitude = 52.3
      val longitude = 56.7

      val trails = 100
      val rhs = 0 until trails map { _ => RelativeHumidityGeneratorImpl.generate(temperature, latitude, longitude) }

      rhs must haveSize(trails)
      rhs must contain(be_<(0.5)).atLeastOnce // { (x : Double) => x must beLessThan(0.5) }
    }
  }

}
