import java.util.regex.Pattern

import com.cheng.weathersimulation.simulators.WeatherSimulator
import com.cheng.weathersimulation.utils.PrettyPrinter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.specs2.mutable._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by cheng on 7/10/2016.
  */

object WeatherSimulatorTest extends Specification {

  private val file = "elevation.bmp"

  "Weather Simulator" should {

    "generate 1 weather data when num = 1 and startDate is before endDate" in {
      val simulator = WeatherSimulator(file)

      val startDateTime = DateTime.now()
      val offset = 20 // 20 days
      val endDateTime = startDateTime.plusDays(offset)

      val num = 1

      val results = simulator.simulate(num, startDateTime, endDateTime)

      Await.result(results, Duration.Inf).size must beEqualTo(num)
    }

    "generate 10 weather data when num = 10 and startDate is before endDate" in {
      val simulator = WeatherSimulator(file)

      val startDateTime = DateTime.now()
      val offset = 20 // 20 days
      val endDateTime = startDateTime.plusDays(offset)

      val num = 10

      val results = simulator.simulate(num, startDateTime, endDateTime)

      Await.result(results, Duration.Inf).size must beEqualTo(num)
    }

    import org.specs2.concurrent.ExecutionEnv
    "generate an output string in the required format" in {implicit ee : ExecutionEnv =>
      val simulator = WeatherSimulator(file)

      val startDateTime = DateTime.now()
      val offset = 20 // 20 days
      val endDateTime = startDateTime.plusDays(offset)

      val num = 1

      val results = simulator.simulate(num, startDateTime, endDateTime)

      Await.result(results, Duration.Inf).size must beEqualTo(num)

      results map { rs =>
        val r = rs(0)
        val outputStr = r.toString
        outputStr must contain('|')
        val outputs = outputStr.split(Pattern.quote("|"))

        PrettyPrinter.prettyCityName(r.city.toString) must beEqualTo(outputs(0))

        val latLongStr = Array(r.latitude, r.longitude) map {_.toString} mkString(",")
        val elevationStr = r.elevation.toString // avoid to print decimal .0
        val positionStr = Array(latLongStr, elevationStr) mkString(",")
        positionStr must beEqualTo(outputs(1))

        val dateTimeStr =  DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ").print(r.dateTime)
        dateTimeStr must beEqualTo(outputs(2))

        r.condition.toString.toLowerCase.capitalize must beEqualTo(outputs(3))

        val temperature = r.temperature
        val tempStr = if(temperature >= 0) "+" + f"$temperature%.1f" else f"$temperature%.1f"
        tempStr must beEqualTo(outputs(4))

        val hPaPressure = r.pressure/100
        val pressureStr = f"$hPaPressure%.1f"
        pressureStr must beEqualTo(outputs(5))

        (r.humidity*100).toInt.toString must beEqualTo(outputs(6))
      } await
    }


  }
}
