import com.cheng.weathersimulation.services.WeatherConditionGeneratorImpl
import org.specs2.mutable._
import com.cheng.weathersimulation.models.WeatherConditions._
/**
  * Created by cheng on 7/10/2016.
  */
object WeatherConditionGeneratorTest extends Specification {

  "Weather Condition Generator" should {

    "generate Rain likely with normal temperature, high humidity and small latitude (tiny trial)" in {
      val temperature = 17.2
      val rh = 0.972
      val latitude = 12.5
      val longitude = 123.4

      val trials = 10
      val results = (0 until trials) map { _ => WeatherConditionGeneratorImpl.generate(temperature, rh, latitude, longitude) }

      results filter(_ == RAIN) must have size(be_>=(trials/3))
    }

    "generate Rain likely with normal temperature, high humidity and small latitude (small trial)" in {
      val temperature = 17.2
      val rh = 0.932
      val latitude = 12.5
      val longitude = 123.4

      val trials = 1000
      val results = (0 until trials) map { _ => WeatherConditionGeneratorImpl.generate(temperature, rh, latitude, longitude) }

      results filter(_ == RAIN) must have size(be_>=(trials/3))
    }

    "generate Sunny mostly with high temperature, normal-high humidity and normal latitude (tiny trial)" in {
      val temperature = 31.2
      val rh = 0.762
      val latitude = 32.5
      val longitude = 123.4

      val trials = 10
      val results = (0 until trials) map { _ => WeatherConditionGeneratorImpl.generate(temperature, rh, latitude, longitude) }

      results filter(_ == SUNNY) must have size(be_>=(trials/2))
    }

    "generate Sunny mostly with high temperature, high humidity and normal latitude (small trial)" in {
      val temperature = 30.2
      val rh = 0.632
      val latitude = 42.5
      val longitude = 123.4

      val trials = 1000
      val results = (0 until trials) map { _ => WeatherConditionGeneratorImpl.generate(temperature, rh, latitude, longitude) }

      results filter(_ == SUNNY) must have size(be_>=(trials/2))
    }

    "generate Snow and Sunny with temperature below 0, high humidity and normal latitude (tiny trial)" in {
      val temperature = -3.2
      val rh = 0.912
      val latitude = 42.5
      val longitude = 53.4

      val trials = 10
      val results = (0 until trials) map { _ => WeatherConditionGeneratorImpl.generate(temperature, rh, latitude, longitude) }

      results filter(_ == SNOW) must have size(be_>(0))
      results filter(_ == RAIN) must have size(be_==(0))
      results filter(_ == SUNNY) must have size(be_>(0))
    }

    "do not generate Rain with temperature below 0 (small trial)" in {
      val temperature = -3.2
      val rh = 0.912
      val latitude = 42.5
      val longitude = 53.4

      val trials = 1000
      val results = (0 until trials) map { _ => WeatherConditionGeneratorImpl.generate(temperature, rh, latitude, longitude) }

      results filter(_ == RAIN) must have size(be_==(0))
    }

    "generate Rain and Sunny with temperature above 0, high humidity and normal latitude (tiny trial)" in {
      val temperature = 3.2
      val rh = 0.912
      val latitude = 42.5
      val longitude = 53.4

      val trials = 10
      val results = (0 until trials) map { _ => WeatherConditionGeneratorImpl.generate(temperature, rh, latitude, longitude) }

      results filter(_ == SNOW) must have size(be_==(0))
      results filter(_ == RAIN) must have size(be_>(0))
      results filter(_ == SUNNY) must have size(be_>(0))
    }

    "do not generate Snow with temperature above 0 (small trial)" in {
      val temperature = 3.2
      val rh = 0.912
      val latitude = 42.5
      val longitude = 53.4

      val trials = 1000
      val results = (0 until trials) map { _ => WeatherConditionGeneratorImpl.generate(temperature, rh, latitude, longitude) }

      results filter(_ == SNOW) must have size(be_==(0))
    }

    "do not generate Rain with low humidity (small trial)" in {
      val temperature = 3.2
      val rh = 0.212
      val latitude = 7.4
      val longitude = 53.4

      val trials = 1000
      val results = (0 until trials) map { _ => WeatherConditionGeneratorImpl.generate(temperature, rh, latitude, longitude) }

      results filter(_ == RAIN) must have size(be_==(0))
    }

    "do not generate Snow with low humidity (small trial)" in {
      val temperature = -0.2
      val rh = 0.212
      val latitude = 77.4
      val longitude = 53.4

      val trials = 1000
      val results = (0 until trials) map { _ => WeatherConditionGeneratorImpl.generate(temperature, rh, latitude, longitude) }

      results filter(_ == SNOW) must have size(be_==(0))
    }

  }

}
