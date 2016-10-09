name := "WeatherDataSimulation"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  // force to pick a version to avoid conflicts among dependencies
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.scala-lang" % "scala-compiler" % "2.11.8",
  // DateTime
  "joda-time" % "joda-time" % "2.9.4",
  "org.joda" % "joda-convert" % "1.8",
  // numeric library
  "org.scalanlp" %% "breeze" % "0.12",
//  "org.scalanlp" %% "breeze-natives" % "0.12"
  // test
  "org.specs2" % "specs2_2.11" % "3.7",
  "org.specs2" % "specs2-matcher-extra_2.11" % "3.7"
)
    