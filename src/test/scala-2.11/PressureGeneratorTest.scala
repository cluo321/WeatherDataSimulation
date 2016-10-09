import com.cheng.weathersimulation.services.PressureGeneratorImpl
import org.specs2.mutable._

/**
  * Created by cheng on 7/10/2016.
  */
object PressureGeneratorTest extends Specification {

  "Pressure Generator" should {

    "large value at low elevation and normal temperature" in {
      val temperature = 23.2
      val elevation = 39
      val pressure = PressureGeneratorImpl.generate(temperature, elevation)
      val hPaPressure = pressure/100

      hPaPressure must beBetween(1000.0, 1100.0)
    }

    "small value at high elevation and normal temperature" in {
      val temperature = 13.0
      val elevation = 3000
      val pressure = PressureGeneratorImpl.generate(temperature, elevation)
      val hPaPressure = pressure/100

      hPaPressure must beBetween(500.0, 1000.0)
    }

  }

}
