package com.cheng.weathersimulation.utils

/**
  * Created by cheng on 9/10/2016.
  */

object PrettyPrinter {

  def prettyCityName(name : String) : String = {
    if(name.contains('_')) {
      val parts = name.split("_")
      (parts map { p => p.toLowerCase.capitalize }).mkString(" ")
    }else {
      name.toString.toLowerCase.capitalize
    }
  }

}
