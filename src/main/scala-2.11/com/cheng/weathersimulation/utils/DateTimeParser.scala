package com.cheng.weathersimulation.utils

import org.joda.time.DateTime

import scala.util.Try

/**
  * Created by cheng on 6/10/2016.
  */
object DateTimeParser {

  private def parseToYearMonthDay(dateStr: String) : Seq[Int] = {
    if(!dateStr.contains("/")){
      throw new IllegalArgumentException(s"Input Argument $dateStr does not contain separator /.")
    }

    // split to obtain year, month, day
    val numStrs : Seq[String] = dateStr.split("/")
    if(3 != numStrs.size){
      throw new IllegalArgumentException(s"Input Argument $dateStr does not contain enough Year/Month/Day.")
    }

    val nums = numStrs map {numStr =>
      Try(Integer.parseInt(numStr))
    }

    if(nums.foldLeft(true)((r : Boolean, elem : Try[Int]) => r && elem.isSuccess)){
      nums map {_.get}
    }else{
      throw new IllegalArgumentException(s"Input Argument $dateStr cannot be parsed properly.")
    }
  }

  // I should use Seq[String] as input args to make it more general
  // But for this specific case, it is ok
  def checkAndParseDate(dateStr1: String, dateStr2: String) : Seq[DateTime] = {
    val date1 = Try(parseToYearMonthDay(dateStr1))
    val date2 = Try(parseToYearMonthDay(dateStr2))
    (date1.isSuccess, date2.isSuccess) match {
      case (true, true) => //assume 0 hour of the day
        val dates = Seq(date1.get, date2.get) map { d => new DateTime(d(0), d(1), d(2), 0, 0) }
        // sort to make start date before end date
        dates.sortWith(_.getMillis <= _.getMillis)
      case (true, false) => throw date2.failed.get
      case (false, true) => throw date1.failed.get
      case _ => throw new IllegalArgumentException(s"Input Arguments cannot be parsed properly.")
    }
  }

}
