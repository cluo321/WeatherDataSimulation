import com.cheng.weathersimulation.services.TemperatureGeneratorImpl
import org.joda.time.DateTime
import org.specs2.mutable._

/**
  * Created by cheng on 7/10/2016.
  */
object TemperatureGeneratorTest extends Specification {

  import com.cheng.weathersimulation.models.City._
  "Temperature Generator" should {

    "generate low temperature when it is winter in Sydney" in {
      val city = SYDNEY
      val winter = new DateTime(2016, 7, 1, 0, 0)

      val trials = 10
      val results = 0 until trials map { _ => TemperatureGeneratorImpl.generate(city, winter)  }

      results must contain { (x : Float) => x must beBetween(0.0f, 25.0f) }
    }

    "generate normal to hot temperature when it is Summer in Sydney" in {
      val city = SYDNEY
      val winter = new DateTime(2016, 1, 4, 0, 0)

      val trials = 10
      val results = 0 until trials map { _ => TemperatureGeneratorImpl.generate(city, winter)  }

      results must contain { (x : Float) => x must beBetween(15.0f, 35.0f) }
    }

  }

}
