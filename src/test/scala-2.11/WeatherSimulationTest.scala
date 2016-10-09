import java.io.File

import com.cheng.weathersimulation.simulators.WeatherSimulator
import com.cheng.weathersimulation.utils.{DateTimeParser, SimulatorResultRecorder}
import org.joda.time.DateTime
import org.specs2.matcher.FileMatchers
import org.specs2.mutable._

import org.specs2.concurrent.ExecutionEnv

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by cheng on 7/10/2016.
  */
object WeatherSimulationTest extends Specification with FileMatchers {sequential

  "Weather Simulation" should {

    "generate correct DateTimes by using DateTimeParser with correct inputs" in {
      val startStr = "2016/10/1"
      val endStr = "2016/11/1"
      val startDateTime  = new DateTime(2016, 10, 1, 0, 0)
      val endDateTime = new DateTime(2016, 11, 1, 0, 0)

      val dateTimes = DateTimeParser.checkAndParseDate(startStr, endStr)

      (dateTimes(0).getMillis/1000) must beEqualTo(startDateTime.getMillis/1000)
      (dateTimes(1).getMillis/1000) must beEqualTo(endDateTime.getMillis/1000)
    }

    "accept correct inputs in random order" in {
      val startStr = "2016/10/1"
      val endStr = "2016/11/1"
      val startDateTime  = new DateTime(2016, 10, 1, 0, 0)
      val endDateTime = new DateTime(2016, 11, 1, 0, 0)

      val dateTimes = DateTimeParser.checkAndParseDate(endStr, startStr)

      (dateTimes(0).getMillis/1000) must beEqualTo(startDateTime.getMillis/1000)
      (dateTimes(1).getMillis/1000) must beEqualTo(endDateTime.getMillis/1000)
    }

    "throw IllegalArgumentException with any input lacking a value" in {
      val startStr = "2016/10"
      val endStr = "2016/11/1"

      DateTimeParser.checkAndParseDate(startStr, endStr) must throwAn[IllegalArgumentException]
    }

    "throw IllegalArgumentException with any input with more separators" in {
      val startStr = "2016/10/1"
      val endStr = "2016/11/1/0"

      DateTimeParser.checkAndParseDate(startStr, endStr) must throwAn[IllegalArgumentException]
    }

    "throw IllegalArgumentException with any input using incorrect separator" in {
      val startStr = "2016/10/1"
      val endStr = """2016\11"""

      DateTimeParser.checkAndParseDate(startStr, endStr) must throwAn[IllegalArgumentException]
    }

    "throw IllegalArgumentException with an empty string" in {
      val startStr = ""
      val endStr = "2016/11/1"

      DateTimeParser.checkAndParseDate(startStr, endStr) must throwAn[IllegalArgumentException]
    }

    "generate an output.txt in current exec directory to store results" in { implicit ee : ExecutionEnv =>
      val fileName = "elevation.bmp"
      val simulator = WeatherSimulator(fileName)
      val numOfSimulations = 11

      val start  = new DateTime(2016, 10, 1, 0, 0)
      val end = new DateTime(2016, 11, 1, 0, 0)

      val results = SimulatorResultRecorder.process(simulator.simulate(numOfSimulations, start, end))(numOfSimulations)
      Await.ready(results, 10 seconds)

      // check that an output exists
      val filePath = new File(".").getCanonicalPath + File.separator + "output.txt"
      new File(filePath) must beAFile
    }

    "generate an output.txt with correct number of lines" in { implicit ee : ExecutionEnv =>
      val fileName = "elevation.bmp"
      val simulator = WeatherSimulator(fileName)
      val numOfSimulations = 10

      val start  = new DateTime(2016, 10, 1, 0, 0)
      val end = new DateTime(2016, 11, 1, 0, 0)

      val results = SimulatorResultRecorder.process(simulator.simulate(numOfSimulations, start, end))(numOfSimulations)
      Await.ready(results, 10 seconds)

      // check that an output exists
      val filePath = new File(".").getCanonicalPath + File.separator + "output.txt"
      val file = new File(filePath)
      file must beAFile

      // file contains numOfSimulations lines
      io.Source.fromFile(file).getLines().size must beEqualTo(numOfSimulations)
    }

    "generate an output.txt with correct number of elements in each line" in { implicit ee : ExecutionEnv =>
      val fileName = "elevation.bmp"
      val simulator = WeatherSimulator(fileName)
      val numOfSimulations = 10

      val start  = new DateTime(2016, 10, 1, 0, 0)
      val end = new DateTime(2016, 11, 1, 0, 0)

      val results = SimulatorResultRecorder.process(simulator.simulate(numOfSimulations, start, end))(numOfSimulations)
      Await.ready(results, 10 seconds)

      // check that an output exists
      val filePath = new File(".").getCanonicalPath + File.separator + "output.txt"
      val file = new File(filePath)
      file must beAFile

      // file contains numOfSimulations lines
      val contents = io.Source.fromFile(file).getLines
      contents.size must beEqualTo(numOfSimulations)

      // each line contains 7 elements
      contents map { c => c.split("\\|").size } must contain(be_==(7)).forall
    }

  }

}
