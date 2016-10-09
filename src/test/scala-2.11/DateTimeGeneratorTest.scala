import com.cheng.weathersimulation.services.DateTimeGeneratorImpl
import org.joda.time.DateTime
import org.specs2.mutable._

/**
  * Created by cheng on 7/10/2016.
  */
object DateTimeGeneratorTest extends Specification {

  "DateTime Generator" should {

    "generate a DateTime between given start and end DateTimes" in {
      val start = DateTime.now
      val end = start.plusDays(10)

      val dateTime = DateTimeGeneratorImpl.generate(start, end)

      dateTime.getMillis must beBetween(start.getMillis, end.getMillis)
    }

    "return start when given start and end DateTimes are equal" in {
      val start = DateTime.now
      val end = start

      val dateTime = DateTimeGeneratorImpl.generate(start, end)

      dateTime.getMillis must beEqualTo(start.getMillis)
    }
  }

}
